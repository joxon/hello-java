USE ap;

CHECK TABLE vendors;
 
CHECK TABLE vendors, invoices, terms, invoices_outstanding;
 
CHECK TABLE vendors, invoices FAST;