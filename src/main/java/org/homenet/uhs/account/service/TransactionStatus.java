package org.homenet.uhs.account.service;

import org.homenet.uhs.transaction.model.Transaction;

/**
 * @author uh on 2017/12/17.
 */
final class TransactionStatus {
    private final Transaction transaction;
    private final TransactionState transactionState;
    private final String message;

    TransactionStatus(Transaction transaction, TransactionState transactionState) {
        this.transaction = transaction;
        this.transactionState = transactionState;
        this.message = null;
    }

    TransactionStatus(Transaction transaction, TransactionState transactionState, String message) {
        this.transaction = transaction;
        this.transactionState = transactionState;
        this.message = message;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public TransactionState getTransactionState() {
        return transactionState;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append("[Transaction from ")
                .append(transaction.getOriginAccount().getId())
                .append(" to ")
                .append(transaction.getDestinationAccount().getId())
                .append(" Amount: ")
                .append(transaction.getPreTaxAmount())
                .append(" Taxed: ")
                .append(transaction.getPreTaxAmount() - transaction.getAfterTaxAmount())
                .append(" - Status: ")
                .append(transactionState.name());

        if(TransactionState.REJECTED.equals(transactionState)){
            sb.append(" Reason: ")
                    .append(message);
        }

        return sb.append("]").toString();

    }
}
