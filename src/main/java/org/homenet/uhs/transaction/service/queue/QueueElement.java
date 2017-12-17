package org.homenet.uhs.transaction.service.queue;

import java.time.LocalDateTime;

/**
 * @author uh on 2017/12/16.
 */
public class QueueElement<T> implements Comparable<QueueElement> {
    private final LocalDateTime creationDate;
    private final T element;

    public QueueElement(T element) {
        super();
        this.element = element;
        this.creationDate = LocalDateTime.now();
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public T getElement() {
        return element;
    }

    @Override
    public int compareTo(QueueElement o) {
        return this.creationDate.compareTo(o.creationDate);
    }
}
