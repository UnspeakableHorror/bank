package org.homenet.uhs.transaction.model;

import static org.homenet.uhs.constants.Countries.ARGENTINA;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.homenet.uhs.account.model.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by carlosgagliardi on 12/18/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class SameBankNationalTransactionTest {

	@Test
	public void testSameBankTaxIsZero(){

		Account origin = mock(Account.class);
		Account destination = mock(Account.class);

		when(origin.getCountry()).thenReturn(ARGENTINA);
		when(destination.getCountry()).thenReturn(ARGENTINA);

		final Double amount = 100.0;

		SameBankNationalTransaction transaction = new SameBankNationalTransaction(origin, destination, amount);

		assertEquals(amount, transaction.getPreTaxAmount());
		assertEquals(amount, transaction.getAfterTaxAmount());
	}

	@Test
	public void testSameBankTransaction(){
		Account origin = new Account(1L, "San", ARGENTINA, 200.0);
		Account destination = new Account(2L, "San", ARGENTINA, 0.0);

		final Double amount = 100.0;
		final Transaction transaction = TransactionFactory.getTransaction(origin, destination, amount);

		origin.addTransaction(transaction);
		destination.addTransaction(transaction);

		assertEquals(amount, origin.getBalance());
		assertEquals(amount, destination.getBalance());
	}
}
