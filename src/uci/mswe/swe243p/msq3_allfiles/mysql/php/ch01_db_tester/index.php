<?php
    $query = 
        "SELECT vendor_name, invoice_number, invoice_total 
         FROM vendors INNER JOIN invoices 
             ON vendors.vendor_id = invoices.vendor_id 
         WHERE invoice_total >= 500 
         ORDER BY vendor_name, invoice_total DESC";            
    
    $dsn = 'mysql:host=localhost;dbname=ap';
    $username = 'ap_tester';
    $password = 'sesame';

    try {
        $db = new PDO($dsn, $username, $password);
    } catch (PDOException $e) {
        $error_message = $e->getMessage();
        echo $error_message;
        exit();
    }
    
    $statement = $db->prepare($query);
    $statement->execute();
    $rows = $statement->fetchAll();    
?>
<!DOCTYPE html>
<html>
    <head>
        <title>DB Test</title>
    </head>
    <body>
        <h1>Invoices with totals over 500:</h1>

        <?php foreach ($rows as $row) : ?>
        <p>
            Vendor: <?php echo $row['vendor_name']; ?><br/>
            Invoice No: <?php echo $row['invoice_number']; ?><br/>
            Total: $<?php echo number_format($row['invoice_total'], 2); ?>
        </p>
        <?php endforeach; ?>

    </body>
</html>