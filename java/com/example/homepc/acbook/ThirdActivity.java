package com.example.homepc.acbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

/**
 * Created by home pc on 26/01/2018.
 */

public class ThirdActivity extends Activity {

    private Context mContext=ThirdActivity.this;

    private static final int REQUEST = 112;

    DatabaseHandler db;
    private static final String TABLE_CONTACTS = "cashManager";
    private static final String TABLE_SBI = "sbiTable";
    private static final String TABLE_HDFC = "hdfcTable";
    private static final String TABLE_HDFC_CC = "hdfcCCTable";
    Context context;
    TextView tv_avalebalCashBalance3;
    TextView tv_avalebalSBIBalance3;
    TextView tv_avalebalHDFCBalance3;
    TextView tv_avalebalHDFCCCBalance3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        context = getApplicationContext();
        db = new DatabaseHandler(this);

        tv_avalebalCashBalance3 = (TextView) findViewById(R.id.cashbal3);
        tv_avalebalSBIBalance3 = (TextView) findViewById(R.id.sbibal3);
        tv_avalebalHDFCBalance3 = (TextView) findViewById(R.id.hdfcbal3);
        tv_avalebalHDFCCCBalance3 = (TextView) findViewById(R.id.hdfcccbal3);

        int avalebalRecordLineInCashTable = db.getDetailsCountFromTable(TABLE_CONTACTS);
        int avalebalBalanceInCash = db.getContactFromTable(avalebalRecordLineInCashTable, TABLE_CONTACTS).get_clear();
        tv_avalebalCashBalance3.setText(avalebalBalanceInCash + "");

        int avalebalRecordLineInSBITable = db.getDetailsCountFromTable(TABLE_SBI);
        int avalebalBalanceInSBI = db.getContactFromTable(avalebalRecordLineInSBITable, TABLE_SBI).get_clear();
        tv_avalebalSBIBalance3.setText(avalebalBalanceInSBI + "");

        int avalebalRecordLineInHDFCTable = db.getDetailsCountFromTable(TABLE_HDFC);
        int avalebalBalanceInHDFC = db.getContactFromTable(avalebalRecordLineInHDFCTable, TABLE_HDFC).get_clear();
        tv_avalebalHDFCBalance3.setText(avalebalBalanceInHDFC + "");

        int avalebalRecordLineInHDFCCCTable = db.getDetailsCountFromTable(TABLE_HDFC_CC);
        int avalebalBalanceInHDFCCC = db.getContactFromTable(avalebalRecordLineInHDFCCCTable, TABLE_HDFC_CC).get_clear();
        tv_avalebalHDFCCCBalance3.setText(avalebalBalanceInHDFCCC + "");
    }

    public void showCashTable(View view) {
        Intent secondActivityIntent = new Intent(this, SecondActivity.class);
        secondActivityIntent.putExtra("Table", TABLE_CONTACTS);
        startActivity(secondActivityIntent);
    }

    public void showSBITable(View view) {
        Intent secondActivityIntent = new Intent(this, SecondActivity.class);
        secondActivityIntent.putExtra("Table", TABLE_SBI);
        startActivity(secondActivityIntent);
    }

    public void showHDFCTable(View view) {
        Intent secondActivityIntent = new Intent(this, SecondActivity.class);
        secondActivityIntent.putExtra("Table", TABLE_HDFC);
        startActivity(secondActivityIntent);
    }

    public void showHDFCCCTable(View view) {
        Intent secondActivityIntent = new Intent(this, SecondActivity.class);
        secondActivityIntent.putExtra("Table", TABLE_HDFC_CC);
        startActivity(secondActivityIntent);
    }

    public void deleteAll(View view) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setMessage("Are you Sure...");

        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        db.deleteAllRecordAllTable();
                        tv_avalebalCashBalance3.setText("0");
                        tv_avalebalSBIBalance3.setText("0");
                        tv_avalebalHDFCBalance3.setText("0");
                        tv_avalebalHDFCCCBalance3.setText("0");
                        // int recordInDbAfterDelete = db.getDetailsCount();
                        setResult(444);
                        finish();
                    }
                }
        );

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getApplicationContext(), "Be carefull next time", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

        //Toast.makeText(getApplicationContext(), "Database Cleared.../", Toast.LENGTH_SHORT).show();


    void backToMain(View view) {
        setResult(RESULT_OK);
        finish();
    }

    void printAllTable(View v){
             PrintFileHandling pfh = new PrintFileHandling();
             if(pfh.isExternalStorageWritable()){
                 printFile();
             }else{
                 Toast.makeText(getApplicationContext(), "Storage not writable..!!", Toast.LENGTH_SHORT).show();
             }
    }

    void printFile(){

        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS, REQUEST );
        }
        final Calendar c = Calendar.getInstance();
        String dte = c.get(Calendar.DAY_OF_MONTH)+"_"+c.get(Calendar.MONTH)+"_"+c.get(Calendar.YEAR);
        PrintFileHandling cashFile = new PrintFileHandling("CashFile_"+dte+".txt");
        cashFile.writeFile(getTableData(TABLE_CONTACTS));

        PrintFileHandling sbiFile = new PrintFileHandling("SBIFile_"+dte+".txt");
        sbiFile.writeFile(getTableData(TABLE_SBI));

        PrintFileHandling hdfcFile = new PrintFileHandling("HDFCFile_"+dte+".txt");
        hdfcFile.writeFile(getTableData(TABLE_HDFC));

        PrintFileHandling hdfcccFile = new PrintFileHandling("HDFCCCFile_"+dte+".txt");
        hdfcccFile.writeFile(getTableData(TABLE_HDFC_CC));

        Toast.makeText(getApplicationContext(), "File stored.", Toast.LENGTH_SHORT).show();
    }

    String getTableData(String tableToShow) {
        String data = tableToShow + "\n*********************\n";
        List<Cash> fullDbRecordInList = db.getAllContactsFromTable(tableToShow);

        for (Cash cn : fullDbRecordInList) {

            String printId_Str = cn.get_id() + "";
            String printDate = cn.get_date();
            String printMoneyInBuzz_Str = cn.get_money() + "";
            int printTypeOfBuzz_Int = cn.get_tt();
            String printTypeOfBuzz_Str = "";
            if (printTypeOfBuzz_Int == 1) {
                printTypeOfBuzz_Str = "Credit";
            } else {
                printTypeOfBuzz_Str = "Debit";
            }
            String printBuzzCause = cn.get_pur();
            String printMoneyAfterBuzz = cn.get_clear() + "";

            data = data + printId_Str +":"+printDate+":"+printMoneyInBuzz_Str+":"+printTypeOfBuzz_Str+":"+printBuzzCause+":"+printMoneyAfterBuzz+"\n";

        }
        data = data + "\n*************End File******************";
        return data;
    }
}
