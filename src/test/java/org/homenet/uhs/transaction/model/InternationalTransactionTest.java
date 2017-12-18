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
public class InternationalTransactionTest {

	@Test
	public void testInternationalTransactionIsFivePercent(){

		Account origin = mock(Account.class);
		Account destination = mock(Account.class);

		when(origin.getCountry()).thenReturn(ARGENTINA);
		when(destination.getCountry()).thenReturn(ARGENTINA);

		final Double amount = 100.0;
		final Double taxed = amount * InternationalTransaction.TAX_PERCENTAGE;

		InternationalTransaction transaction = new InternationalTransaction(origin, destination, amount);

		assertEquals(transaction.getPreTaxAmount(), amount);
		assertEquals(transaction.getAfterTaxAmount(), new Double(amount - taxed));
	}
}
