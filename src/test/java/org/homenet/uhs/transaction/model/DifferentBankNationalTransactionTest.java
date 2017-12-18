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
public class DifferentBankNationalTransactionTest {

	@Test
	public void testDifferentBankNationalTaxIsOnePercent(){

		Account origin = mock(Account.class);
		Account destination = mock(Account.class);

		when(origin.getCountry()).thenReturn(ARGENTINA);
		when(destination.getCountry()).thenReturn(ARGENTINA);

		final Double amount = 100.0;
		final Double taxed = amount * DifferentBankNationalTransaction.TAX_PERCENTAGE;

		DifferentBankNationalTransaction transaction = new DifferentBankNationalTransaction(origin, destination, amount);

		assertEquals(transaction.getPreTaxAmount(), amount);
		assertEquals(transaction.getAfterTaxAmount(), new Double(amount - taxed));
	}
}
