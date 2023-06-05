package com.training.expense.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.expense.model.Category;
import com.training.expense.model.PaymentMode;
import com.training.expense.model.Transaction;
import com.training.expense.model.TransactionNotFoundException;
import com.training.expense.repository.CategoryRepository;
import com.training.expense.repository.PaymentModeRepository;
import com.training.expense.repository.TransactionRepository;


@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionrepository;

    @Autowired
    PaymentModeRepository paymentModeRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public void addTransaction(Transaction transaction) {
        if (transaction.getDetail().equalsIgnoreCase("expense")) {
            Optional<PaymentMode> type = paymentModeRepository.findByMode(transaction.getPaymentMode().getMode());
            if (type.isPresent()) {
                PaymentMode paymentMode = type.get();
                if (paymentMode.getInitial_amount() >= transaction.getAmount()) {
                    paymentMode.setInitial_amount(paymentMode.getInitial_amount() - transaction.getAmount());
                    paymentModeRepository.save(paymentMode);
                } else {
                    throw new TransactionNotFoundException("Insufficient balance");
                }
            }
        } else {
            Optional<PaymentMode> type = paymentModeRepository.findByMode(transaction.getPaymentMode().getMode());
            if (type.isPresent()) {
                PaymentMode paymentMode = type.get();
                paymentMode.setInitial_amount(paymentMode.getInitial_amount() + transaction.getAmount());
                paymentModeRepository.save(paymentMode);
            }
        }

        // Retrieve existing category from the database or create a new one if necessary
        Category category = categoryRepository.findByName(transaction.getCategory().getName())
                .orElseGet(() -> categoryRepository.save(transaction.getCategory()));

        // Assign the existing or newly created category to the transaction
        transaction.setCategory(category);

        // Retrieve existing payment mode from the database or create a new one if necessary
        PaymentMode paymentMode = paymentModeRepository.findByMode(transaction.getPaymentMode().getMode())
                .orElseGet(() -> paymentModeRepository.save(transaction.getPaymentMode()));

        // Assign the existing or newly created payment mode to the transaction
        transaction.setPaymentMode(paymentMode);

        // Save the transaction
        transactionrepository.save(transaction);
    }

    public List<Transaction> getTransaction() {
        List<Transaction> transaction = (List<Transaction>) transactionrepository.findAll();
        return transaction;
    }

    
    public void updateTransaction(Transaction updatedTransaction) {
        Transaction existingTransaction = transactionrepository.findById(updatedTransaction.getId())
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));

        // Update the transaction details and amount
        existingTransaction.setDetail(updatedTransaction.getDetail());
        existingTransaction.setAmount(updatedTransaction.getAmount());
        existingTransaction.setDescription(updatedTransaction.getDescription());

        // Check if the payment mode has changed
        if (!existingTransaction.getPaymentMode().getMode().equals(updatedTransaction.getPaymentMode().getMode())) {
            // Retrieve the existing payment mode from the database
            Optional<PaymentMode> existingPaymentMode = paymentModeRepository.findByMode(existingTransaction.getPaymentMode().getMode());

            if (existingPaymentMode.isPresent()) {
                PaymentMode oldPaymentMode = existingPaymentMode.get();
                if (existingTransaction.getDetail().equalsIgnoreCase("expense")) {
                    oldPaymentMode.setInitial_amount(oldPaymentMode.getInitial_amount() + existingTransaction.getAmount());
                } else if (existingTransaction.getDetail().equalsIgnoreCase("income") && oldPaymentMode.getInitial_amount() >= 0) {
                    oldPaymentMode.setInitial_amount(oldPaymentMode.getInitial_amount() - existingTransaction.getAmount());
                } else {
                    oldPaymentMode.setInitial_amount(existingTransaction.getAmount());
                }
                paymentModeRepository.save(oldPaymentMode);
            }

            // Retrieve the new payment mode from the database or create a new one if necessary
            Optional<PaymentMode> newPaymentMode = paymentModeRepository.findByMode(updatedTransaction.getPaymentMode().getMode());
            PaymentMode updatedPaymentMode = newPaymentMode.orElseGet(() -> {
                PaymentMode paymentMode = updatedTransaction.getPaymentMode();
                paymentMode.setInitial_amount(0); // Initialize initial_amount to 0 for new payment mode
                return paymentModeRepository.save(paymentMode);
            });

            if (updatedTransaction.getDetail().equalsIgnoreCase("expense")) {
                updatedPaymentMode.setInitial_amount(updatedPaymentMode.getInitial_amount() - updatedTransaction.getAmount());
            } else if (updatedTransaction.getDetail().equalsIgnoreCase("income")) {
                updatedPaymentMode.setInitial_amount(updatedPaymentMode.getInitial_amount() + updatedTransaction.getAmount());
            }

            paymentModeRepository.save(updatedPaymentMode);

            existingTransaction.setPaymentMode(updatedPaymentMode);
        } else {
            // If payment mode is not changed, update the amount in the existing payment mode
            PaymentMode paymentMode = existingTransaction.getPaymentMode();
            long difference = updatedTransaction.getAmount() - existingTransaction.getAmount();
            if (existingTransaction.getDetail().equalsIgnoreCase("expense")) {
                paymentMode.setInitial_amount(paymentMode.getInitial_amount() + difference);
            } else if (existingTransaction.getDetail().equalsIgnoreCase("income")) {
                paymentMode.setInitial_amount(paymentMode.getInitial_amount() - difference);
            }
            paymentModeRepository.save(paymentMode);
        }

        // Save the updated transaction
        transactionrepository.save(existingTransaction);
    }



    public void deleteTransaction(int id) {
        Optional<Transaction> transaction = transactionrepository.findById(id);
        if (transaction.isPresent()) {
            Transaction trans = transaction.get();
            long amount = trans.getAmount();
            String detail = trans.getDetail();
            PaymentMode mode = trans.getPaymentMode();
            double oldBalance = mode.getInitial_amount();
            if (detail.equals("expense")) {
                double initial_amount = oldBalance + amount;
                mode.setInitial_amount(initial_amount);
                paymentModeRepository.save(mode);
            } else {
                double initial_amount = oldBalance - amount;
                mode.setInitial_amount(initial_amount);
                paymentModeRepository.save(mode);
            }
            transactionrepository.deleteById(id);
        } else {
            throw new TransactionNotFoundException("Transaction id not exist" + id);
        }
    }
}
