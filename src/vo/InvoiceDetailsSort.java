package vo;

import java.util.Comparator;

public class InvoiceDetailsSort implements Comparator<InvoiceDetails> {

	@Override
	public int compare(InvoiceDetails o1, InvoiceDetails o2) {
		if (o1.getInvoiceNo() > o2.getInvoiceNo()){
			return -1;
		}else {
			return 1;
		}
		
	}

}
