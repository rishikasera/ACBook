package com.example.homepc.acbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

/**
 * Created by home pc on 20/01/2018.
 */

public class SecondActivity extends Activity {

    private static final String TABLE_CONTACTS = "cashManager";
    private static final String TABLE_SBI = "sbiTable";
    private static final String TABLE_HDFC = "hdfcTable";
    private static final String TABLE_HDFC_CC = "hdfcCCTable";
    DatabaseHandler db;
    Context context;
    TableLayout tableLayout;
    String tableToShow = "";
    TextView tvTableName ;

    private Context mContext=SecondActivity.this;

    private static final int REQUEST = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        context = getApplicationContext();
        tvTableName = (TextView) findViewById(R.id.tableName);
        Intent intentFromFirstAct = getIntent();
        tableToShow = intentFromFirstAct.getStringExtra("Table");

        tvTableName.setText("Record from : "+tableToShow);

        Log.d("Showing Table: ","**********************************************************");
        Log.d("Showing Table: ", tableToShow);
        Log.d("Showing Table: ","**********************************************************");

        db = new DatabaseHandler(this);

        tableLayout = (TableLayout) findViewById(R.id.tablelayout);
        // Add header row
        TableRow rowHeader = new TableRow(context);
        rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        String[] headerText = {"ID", "DATE", "Money", "D/C", "Cause", "Clear"};

        for (String c : headerText) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(18);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tableLayout.addView(rowHeader);


        Log.d("Fetching row count: ","**********************************************************");
        Log.d("Fetching row count: ", "Fetching row count");
        Log.d("Fetching row count: ","**********************************************************");

        Log.d("Showing Table: ","**********************************************************");
        Log.e("Table->->->->->->->->->", tableToShow);
        Log.d("Showing Table: ","**********************************************************");

        int recordCountInDB = db.getDetailsCountFromTable(tableToShow);

        Log.d("row count: ","**********************************************************");
        Log.d("row count: ", recordCountInDB+"");
        Log.d("row count: ","**********************************************************");

        if (recordCountInDB > 20) {
            int printRecordFrom = recordCountInDB - 16;
            fillTable(printRecordFrom);
        }else {
            fillTable(0);
        }
    }


    void backToMain(View view) {
        finish();
    }


    void fillTable(int printRecordFrom){
        List<Cash> fullDbRecordInList = db.getAllContactsFromTable(tableToShow);

        for (Cash cn : fullDbRecordInList) {
            //String log = "Id: " + cn.get_id() + " ,Date: " + cn.get_date() + " ,Money: " +
            //        cn.get_money() + " , C/D:" + cn.get_tt() + " ,Pur:" + cn.get_pur() + " ,OS:" + cn.get_clear();
            int printId_INT = cn.get_id();
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

            if (printId_INT >= printRecordFrom) {
                TableRow row = new TableRow(context);
                row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                String[] colText = {printId_Str, printDate, printMoneyInBuzz_Str, printTypeOfBuzz_Str, printBuzzCause, printMoneyAfterBuzz};
                for (String text : colText) {
                    TextView tv = new TextView(this);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(16);
                    tv.setPadding(5, 5, 5, 5);
                    tv.setText(text);
                    tv.setMaxWidth(255);
                    tv.setMaxLines(1);
                    row.addView(tv);
                }
                tableLayout.addView(row);
            }
        }
    }


    void printThisTable(View v){
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



        PrintFileHandling file;
        if(tableToShow.equals(TABLE_CONTACTS)){
            file = new PrintFileHandling("CashFile_"+dte+".txt");
        }else if(tableToShow.equals(TABLE_SBI)){
            file = new PrintFileHandling("SBIFile_"+dte+".txt");
        }else if(tableToShow.equals(TABLE_HDFC)){
            file = new PrintFileHandling("HDFCFile_"+dte+".txt");
        }else{
            file = new PrintFileHandling("HDFCCCFile_"+dte+".txt");
        }

        file.writeFile(getTableData(tableToShow));

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