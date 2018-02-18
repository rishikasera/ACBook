package com.example.homepc.acbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TABLE_CASH = "cashManager";
    private static final String TABLE_SBI = "sbiTable";
    private static final String TABLE_HDFC = "hdfcTable";
    private static final String TABLE_HDFC_CC = "hdfcCCTable";
    private Boolean firstTime = null;
    DatabaseHandler db;
    EditText et_buzzMoney;
    EditText et_buzzCause;
    TextView tv_avalebalCashBalance;
    Spinner spinner_buzzType;
    Button btn_buzzDate;
    private Calendar calendar;
    Button btn_1;
    int buzzTypeForCon = 0;
    int buzzSubTypeForCon = 1;
    Button btnSaveRecord ;
    ArrayAdapter<CharSequence> adapter;
    private int year, month, day;
    int avalebalRecordLineInCashTable = 0;
    int avalebalBalanceInCash = 0;
    int avalebalRecordLineInSBITable = 0;
    int avalebalBalanceInSBI = 0;
    int avalebalRecordLineInHDFCTable = 0;
    int avalebalBalanceInHDFC = 0;
    int avalebalRecordLineInHDFCCCTable = 0;
    int avalebalBalanceInHDFCCC = 0;
    private Context mContext=MainActivity.this;
    private static final int REQUEST = 112;


/*###############################################################################################################3
*
*                                                 onCreate
*
* ###############################################################################################################*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS, REQUEST );
        }



        et_buzzMoney = (EditText) findViewById(R.id.pay);
        et_buzzCause = (EditText) findViewById(R.id.cas);
        tv_avalebalCashBalance = (TextView) findViewById(R.id.cashbal);

        db = new DatabaseHandler(this);

        btnSaveRecord = (Button)findViewById(R.id.saveRecordBtn9);

       et_buzzCause.addTextChangedListener(mTextEditorWatcher);

        if(isFirstTime()){


                Intent intent = new Intent(this,FirstTimeActivity.class);
                startActivityForResult(intent,1);
      }else{
            Toast.makeText(MainActivity.this,"Not First Run",Toast.LENGTH_SHORT).show();

        }


            avalebalRecordLineInCashTable = db.getDetailsCountFromTable(TABLE_CASH);
            avalebalBalanceInCash = db.getContactFromTable(avalebalRecordLineInCashTable, TABLE_CASH).get_clear();
            tv_avalebalCashBalance.setText(avalebalBalanceInCash + "");


            avalebalRecordLineInSBITable = db.getDetailsCountFromTable(TABLE_SBI);
            avalebalBalanceInSBI = db.getContactFromTable(avalebalRecordLineInSBITable,TABLE_SBI).get_clear();

            avalebalRecordLineInHDFCTable = db.getDetailsCountFromTable(TABLE_HDFC);
            avalebalBalanceInHDFC = db.getContactFromTable(avalebalRecordLineInHDFCTable,TABLE_HDFC).get_clear();

            avalebalRecordLineInHDFCCCTable = db.getDetailsCountFromTable(TABLE_HDFC_CC);
            avalebalBalanceInHDFCCC = db.getContactFromTable(avalebalRecordLineInHDFCCCTable,TABLE_HDFC_CC).get_clear();

            btn_1 = (Button) findViewById(R.id.button1);

            spinner_buzzType = (Spinner) findViewById(R.id.dc);

            // Create an ArrayAdapter using the string array and a default spinner_buzzType layout
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.debit_array, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner_buzzType
            spinner_buzzType.setAdapter(adapter);
            spinner_buzzType.setOnItemSelectedListener(this);


            btn_buzzDate = (Button) findViewById(R.id.button1);
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            showDate(year, month + 1, day);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                //Toast.makeText(MainActivity.this,"Return Success",Toast.LENGTH_SHORT).show();
                avalebalBalanceInCash = Integer.parseInt(data.getStringExtra("cash12"));
                avalebalBalanceInSBI  = Integer.parseInt(data.getStringExtra("sbi12"));
                avalebalBalanceInHDFC = Integer.parseInt(data.getStringExtra("hdfc12"));
                avalebalBalanceInHDFCCC = Integer.parseInt(data.getStringExtra("hdfccc12"));

                db.addContact2(TABLE_CASH,avalebalBalanceInCash);
                db.addContact2(TABLE_SBI,avalebalBalanceInSBI);
                db.addContact2(TABLE_HDFC,avalebalBalanceInHDFC);
                db.addContact2(TABLE_HDFC_CC,avalebalBalanceInHDFCCC);

                tv_avalebalCashBalance.setText(avalebalBalanceInCash+"");

            }else{
                Toast.makeText(MainActivity.this,"Return Fail",Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == 4){
            if(resultCode == 444) {
                avalebalBalanceInCash = 0;
                avalebalBalanceInSBI  = 0;
                avalebalBalanceInHDFC = 0;
                avalebalBalanceInHDFCCC = 0;
                tv_avalebalCashBalance.setText(avalebalBalanceInCash+"");
               Toast.makeText(getApplicationContext(), "Database Cleared.../", Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == RESULT_OK){
                Toast.makeText(getApplicationContext(), "Return Normaly.../", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(MainActivity.this,"Return Fail lvl 2",Toast.LENGTH_SHORT).show();
            }
        }
    }



/*###############################################################################################################3
*
*                                                 Date Picker
*
* ###############################################################################################################*/

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,int arg1, int arg2, int arg3) {
                    showDate(arg1, arg2+1, arg3);    }
            };

    public void setDate(View view) {
        showDialog(999);
    }

    private void showDate(int year, int month, int day) {
        btn_buzzDate.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

/*###############################################################################################################3
*
*                             Radio Button selection :  BUZZ TYPE
*
* ###############################################################################################################*/

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.BuzzTypeDebit:
                if (checked){
                    et_buzzCause.setVisibility(View.VISIBLE);
                    buzzTypeForCon = 0;
                    setSpinnerOption(1);
                    //Toast.makeText(getBaseContext(), "Debit", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.BuzzTypeCredit:
                if (checked){
                    et_buzzCause.setVisibility(View.VISIBLE);
                    buzzTypeForCon = 1;
                    setSpinnerOption(2);
                    //Toast.makeText(getBaseContext(), "Credit", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.BuzzTypeNDNC:
                if(checked){
                    et_buzzCause.setVisibility(View.INVISIBLE);
                    buzzTypeForCon = 2;
                    setSpinnerOption(3);
                   // Toast.makeText(getBaseContext(), "NDNC", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    void setSpinnerOption(int bizzOp){
        if(bizzOp == 1){
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.debit_array , android.R.layout.simple_spinner_item);
        }else if(bizzOp == 2){
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.credit_array, android.R.layout.simple_spinner_item);
        }else if(bizzOp == 3){
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.ncnd_array, android.R.layout.simple_spinner_item);
        }
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner_buzzType
        spinner_buzzType.setAdapter(adapter);
        spinner_buzzType.setOnItemSelectedListener(this);

    }


 /*###############################################################################################################3
*
*                                Spinner selection :  SUB BUZZ TYPE
*
* ###############################################################################################################*/

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        String op = parent.getItemAtPosition(pos).toString();
        buzzSubTypeForCon  = pos;

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


 /*###############################################################################################################3
*
*                              Store record in database :  All condition here
*                              int buzzTypeForCon = 0;
*                              int buzzSubTypeForCon = 0;
* ###############################################################################################################*/

    String buzzType(){
        String msg = "";
        String type = "";
        String subType = "";
             if(buzzTypeForCon == 0){
                 type = "Debit";
                 if(buzzSubTypeForCon == 0){
                     subType = "Record";
                 }else if(buzzSubTypeForCon == 1){
                     subType = "SBI";
                 }else if(buzzSubTypeForCon == 2){
                     subType = "HDFC";
                 }else if(buzzSubTypeForCon == 3){
                     subType = "HDFC CC";
                 }
             }else if(buzzTypeForCon == 1){
                 type = "Credit";
                 if(buzzSubTypeForCon == 0){
                     subType = "Record";
                 }else if(buzzSubTypeForCon == 1){
                     subType = "SBI";
                 }else if(buzzSubTypeForCon == 2){
                     subType = "HDFC";
                 }else if(buzzSubTypeForCon == 3){
                     subType = "HDFC CC";
                 }
             }else if(buzzTypeForCon == 2){
                 type = "NDNC";
                 if(buzzSubTypeForCon == 0){
                      subType = "1";
                 }else if(buzzSubTypeForCon == 1){
                      subType = "2";
                 }else if(buzzSubTypeForCon == 2){
                      subType = "3";
                 }else if(buzzSubTypeForCon == 3){
                      subType = "4";
                 }else if(buzzSubTypeForCon == 4){
                      subType = "5";
                 }else if(buzzSubTypeForCon == 5){
                     subType = "6";
                 }else if(buzzSubTypeForCon == 6){
                     subType = "7";
                 }else if(buzzSubTypeForCon == 7){
                     subType = "8";
                 }
             }
        msg = "Type : "+type+"\nSubType : "+subType;
        return msg;
    }

    public void saveRecord(View view){
       AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        String usrBuzzMoney_str = et_buzzMoney.getText().toString();
        String usrBuzzCause = et_buzzCause.getText().toString();
        String usrBuzzDate = btn_buzzDate.getText().toString();
        boolean isUsrBuzzCauseEmpty = usrBuzzCause.isEmpty();
        int usrBuzzMoneyLength = usrBuzzMoney_str.length();
        int usrBuzzMoney_int = 0;

        String conStr = "Money : "+usrBuzzMoney_str+"\nCause : "+usrBuzzCause+"\n"+buzzType();

        alertDialogBuilder.setMessage(conStr);
        if(usrBuzzMoneyLength>=6 || usrBuzzMoneyLength==0) {
            Toast.makeText(getBaseContext(), "Num to big or empty", Toast.LENGTH_LONG).show();
            et_buzzMoney.setText("");
            et_buzzCause.setText("");
            spinner_buzzType.setAdapter(adapter);
        }
        else if(isUsrBuzzCauseEmpty && (buzzTypeForCon !=2)) {
            Toast.makeText(getBaseContext(), "Cause Field Empty", Toast.LENGTH_LONG).show();
            et_buzzMoney.setText("");
            et_buzzCause.setText("");
            spinner_buzzType.setAdapter(adapter);
        }
        else {

            alertDialogBuilder.setPositiveButton("yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            recordBuzz();
                        }
                    }
            );

            alertDialogBuilder.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            defaultPos();
                            Toast.makeText(MainActivity.this, "Reentered data", Toast.LENGTH_LONG).show();
                        }
                    }
            );
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    void recordBuzz(){
        String msg = buzzTypeForCon+","+buzzSubTypeForCon+",";

        String usrBuzzMoney_str = et_buzzMoney.getText().toString();
        //String usrBuzzType = spinner_buzzType.getItemAtPosition(buzzTypeSelectedByUsr).toString();
        String usrBuzzCause = et_buzzCause.getText().toString();
        String usrBuzzDate = btn_buzzDate.getText().toString();
        boolean isUsrBuzzCauseEmpty = usrBuzzCause.isEmpty();
        int usrBuzzMoneyLength = usrBuzzMoney_str.length();
        int usrBuzzMoney_int = 0;


        {
            usrBuzzMoney_int  = Integer.parseInt(usrBuzzMoney_str);
            int avalebalBalanceAfterBuzz = 0;
            if(buzzTypeForCon == 0){
                if(buzzSubTypeForCon==0){
                    if(avalebalBalanceInCash < usrBuzzMoney_int){
                        Toast.makeText(getBaseContext(), "Not Sufficient CASH Bal", Toast.LENGTH_LONG).show();
                        defaultPos();
                    }else {
                        avalebalBalanceAfterBuzz = avalebalBalanceInCash - usrBuzzMoney_int;
                        avalebalBalanceInCash = avalebalBalanceAfterBuzz;
                        db.addContactInTable(new Record(usrBuzzDate, usrBuzzMoney_int, 0, usrBuzzCause, avalebalBalanceAfterBuzz), TABLE_CASH);
                        tv_avalebalCashBalance.setText(avalebalBalanceAfterBuzz + "");
                        Toast.makeText(getBaseContext(), "Record Added in Record Table.", Toast.LENGTH_LONG).show();
                    }
                }else if(buzzSubTypeForCon==1){
                    if(avalebalBalanceInSBI < usrBuzzMoney_int){
                        Toast.makeText(getBaseContext(), "Not SBI Sufficient Bal", Toast.LENGTH_LONG).show();
                        defaultPos();
                    }else {
                        avalebalBalanceAfterBuzz = avalebalBalanceInSBI - usrBuzzMoney_int;
                        avalebalBalanceInSBI = avalebalBalanceAfterBuzz;
                        db.addContactInTable(new Record(usrBuzzDate, usrBuzzMoney_int, 0, usrBuzzCause, avalebalBalanceAfterBuzz),TABLE_SBI);
                   //     tv_avalebalSBIBalance.setText(avalebalBalanceAfterBuzz + "");
                        Toast.makeText(getBaseContext(), "Record Added in SBI Table.", Toast.LENGTH_LONG).show();
                    }

                }else if(buzzSubTypeForCon==2){
                    if(avalebalBalanceInHDFC < usrBuzzMoney_int){
                        Toast.makeText(getBaseContext(), "Not Sufficient HDFC Bal", Toast.LENGTH_LONG).show();
                        defaultPos();
                    }else {
                        avalebalBalanceAfterBuzz = avalebalBalanceInHDFC - usrBuzzMoney_int;
                        avalebalBalanceInHDFC = avalebalBalanceAfterBuzz;
                        db.addContactInTable(new Record(usrBuzzDate, usrBuzzMoney_int, 0, usrBuzzCause, avalebalBalanceAfterBuzz),TABLE_HDFC);
                    //    tv_avalebalHDFCBalance.setText(avalebalBalanceAfterBuzz + "");
                        Toast.makeText(getBaseContext(), "Record Added in HDFC Table.", Toast.LENGTH_LONG).show();
                    }

                }else if(buzzSubTypeForCon==3){
                    if(avalebalBalanceInHDFCCC < usrBuzzMoney_int){
                        Toast.makeText(getBaseContext(), "Not Sufficient HDFCCC Bal", Toast.LENGTH_LONG).show();
                        defaultPos();
                    }else {
                        avalebalBalanceAfterBuzz = avalebalBalanceInHDFCCC - usrBuzzMoney_int;
                        avalebalBalanceInHDFCCC = avalebalBalanceAfterBuzz;
                        db.addContactInTable(new Record(usrBuzzDate, usrBuzzMoney_int, 0, usrBuzzCause, avalebalBalanceAfterBuzz),TABLE_HDFC_CC);
                    //    tv_avalebalHDFCCCBalance.setText(avalebalBalanceAfterBuzz + "");
                        Toast.makeText(getBaseContext(), "Record Added in HDFC CC Table.", Toast.LENGTH_LONG).show();
                    }

                }else{
                    msg = msg +"0f,";
                }
            }else if(buzzTypeForCon == 1){
                if(buzzSubTypeForCon == 0){
                    avalebalBalanceAfterBuzz = avalebalBalanceInCash + usrBuzzMoney_int;
                    avalebalBalanceInCash = avalebalBalanceAfterBuzz;
                    db.addContactInTable(new Record(usrBuzzDate, usrBuzzMoney_int, 1, usrBuzzCause, avalebalBalanceAfterBuzz), TABLE_CASH);
                    tv_avalebalCashBalance.setText(avalebalBalanceAfterBuzz + "");
                    Toast.makeText(getBaseContext(), "Record Added in Record Table.", Toast.LENGTH_LONG).show();
                }
                else if(buzzSubTypeForCon == 1){
                    avalebalBalanceAfterBuzz = avalebalBalanceInSBI + usrBuzzMoney_int;
                    avalebalBalanceInSBI = avalebalBalanceAfterBuzz;
                    db.addContactInTable(new Record(usrBuzzDate, usrBuzzMoney_int, 1, usrBuzzCause, avalebalBalanceAfterBuzz),TABLE_SBI);
              //      tv_avalebalSBIBalance.setText(avalebalBalanceAfterBuzz + "");
                    Toast.makeText(getBaseContext(), "Record Added in SBI Table.", Toast.LENGTH_LONG).show();
                }
                else if(buzzSubTypeForCon == 2){
                    avalebalBalanceAfterBuzz = avalebalBalanceInHDFC + usrBuzzMoney_int;
                    avalebalBalanceInHDFC = avalebalBalanceAfterBuzz;
                    db.addContactInTable(new Record(usrBuzzDate, usrBuzzMoney_int, 1, usrBuzzCause, avalebalBalanceAfterBuzz),TABLE_HDFC);
           //         tv_avalebalHDFCBalance.setText(avalebalBalanceAfterBuzz + "");
                    Toast.makeText(getBaseContext(), "Record Added in hdfc Table.", Toast.LENGTH_LONG).show();
                }
                else if(buzzSubTypeForCon == 3){
                    avalebalBalanceAfterBuzz = avalebalBalanceInHDFCCC + usrBuzzMoney_int;
                    avalebalBalanceInHDFCCC = avalebalBalanceAfterBuzz;
                    db.addContactInTable(new Record(usrBuzzDate, usrBuzzMoney_int, 1, usrBuzzCause, avalebalBalanceAfterBuzz),TABLE_HDFC_CC);
             //////       tv_avalebalHDFCCCBalance.setText(avalebalBalanceAfterBuzz + "");
                    Toast.makeText(getBaseContext(), "Record Added in hdfc CC Table.", Toast.LENGTH_LONG).show();
                }else{
                    msg = msg +"1f,";
                }
            }else if(buzzTypeForCon == 2){
                int avalebalBalanceInCashAfterBuzz = 0;
                int avalebalBalanceInSBIAfterBuzz = 0;
                int avalebalBalanceInHDFCAfterBuzz = 0;
                int avalebalBalanceInHDFCCCAfterBuzz = 0;

                if(buzzSubTypeForCon==0){
                        if(usrBuzzMoney_int > avalebalBalanceInSBI){
                            Toast.makeText(getBaseContext(), "Not Sufficent bal in SBI", Toast.LENGTH_LONG).show();
                            defaultPos();
                        }else{
                            avalebalBalanceInCashAfterBuzz = avalebalBalanceInCash + usrBuzzMoney_int;
                            avalebalBalanceInSBIAfterBuzz = avalebalBalanceInSBI - usrBuzzMoney_int;
                            avalebalBalanceInCash = avalebalBalanceInCashAfterBuzz;
                            avalebalBalanceInSBI = avalebalBalanceInSBIAfterBuzz;
                            db.addContactInTable(new Record(usrBuzzDate, usrBuzzMoney_int, 1, "SBI -> Record", avalebalBalanceInCashAfterBuzz), TABLE_CASH);
                            db.addContactInTable(new Record(usrBuzzDate, usrBuzzMoney_int, 0, "SBI -> Record", avalebalBalanceInSBI),TABLE_SBI);
                            tv_avalebalCashBalance.setText(avalebalBalanceInCashAfterBuzz + "");
                  //          tv_avalebalSBIBalance.setText(avalebalBalanceInSBIAfterBuzz + "");
                            Toast.makeText(getBaseContext(), "Record Added in CASH and SBI Table.", Toast.LENGTH_LONG).show();
                        }
                }else if(buzzSubTypeForCon==1){

                    if(usrBuzzMoney_int > avalebalBalanceInHDFC){
                        Toast.makeText(getBaseContext(), "Not Sufficent bal in HDFC", Toast.LENGTH_LONG).show();
                        defaultPos();
                    }else{
                        avalebalBalanceInCashAfterBuzz = avalebalBalanceInCash + usrBuzzMoney_int;
                        avalebalBalanceInHDFCAfterBuzz = avalebalBalanceInHDFC - usrBuzzMoney_int;
                        avalebalBalanceInCash = avalebalBalanceInCashAfterBuzz;
                        avalebalBalanceInHDFC = avalebalBalanceInHDFCAfterBuzz;
                        db.addContactInTable(new Record(usrBuzzDate, usrBuzzMoney_int, 1, "HDFC -> Record", avalebalBalanceInCashAfterBuzz), TABLE_CASH);
                        db.addContactInTable(new Record(usrBuzzDate, usrBuzzMoney_int, 0, "HDFC -> Record", avalebalBalanceInHDFC),TABLE_HDFC);
                        tv_avalebalCashBalance.setText(avalebalBalanceInCashAfterBuzz + "");
                 //       tv_avalebalHDFCBalance.setText(avalebalBalanceInHDFCAfterBuzz + "");
                        Toast.makeText(getBaseContext(), "Record Added in CASH and HDFC Table.", Toast.LENGTH_LONG).show();
                    }
                }else if(buzzSubTypeForCon==2){

                    if(usrBuzzMoney_int > avalebalBalanceInHDFCCC){
                        Toast.makeText(getBaseContext(), "Not Sufficent bal in HDFCCC", Toast.LENGTH_LONG).show();
                        defaultPos();
                    }else{
                        avalebalBalanceInCashAfterBuzz = avalebalBalanceInCash + usrBuzzMoney_int;
                        avalebalBalanceInHDFCCCAfterBuzz = avalebalBalanceInHDFCCC - usrBuzzMoney_int;
                        avalebalBalanceInCash = avalebalBalanceInCashAfterBuzz;
                        avalebalBalanceInHDFCCC = avalebalBalanceInHDFCCCAfterBuzz;
                        db.addContactInTable(new Record(usrBuzzDate, usrBuzzMoney_int, 1, "HDFCCC -> Record", avalebalBalanceInCashAfterBuzz), TABLE_CASH);
                        db.addContactInTable(new Record(usrBuzzDate, usrBuzzMoney_int, 0, "HDFCCC -> Record", avalebalBalanceInHDFCCC),TABLE_HDFC_CC);
                        tv_avalebalCashBalance.setText(avalebalBalanceInCashAfterBuzz + "");
                  //      tv_avalebalHDFCCCBalance.setText(avalebalBalanceInHDFCCCAfterBuzz + "");
                        Toast.makeText(getBaseContext(), "Record Added in CASH and HDFC CC Table.", Toast.LENGTH_LONG).show();
                    }

                }else if(buzzSubTypeForCon==3){
                    if(usrBuzzMoney_int > avalebalBalanceInHDFC){
                        Toast.makeText(getBaseContext(), "Not Sufficent bal in HDFC", Toast.LENGTH_LONG).show();
                        defaultPos();
                    }else{
                        avalebalBalanceInHDFCCCAfterBuzz = avalebalBalanceInHDFCCC + usrBuzzMoney_int;
                        avalebalBalanceInHDFCAfterBuzz = avalebalBalanceInHDFC - usrBuzzMoney_int;
                        avalebalBalanceInHDFCCC = avalebalBalanceInHDFCCCAfterBuzz;
                        avalebalBalanceInHDFC = avalebalBalanceInHDFCAfterBuzz;
                        db.addContactInTable(new Record(usrBuzzDate, usrBuzzMoney_int, 1, "HDFC -> HDFCCC", avalebalBalanceInHDFCCC),TABLE_HDFC_CC);
                        db.addContactInTable(new Record(usrBuzzDate, usrBuzzMoney_int, 0, "HDFC -> HDFCCC", avalebalBalanceInHDFC),TABLE_HDFC);
                   //     tv_avalebalHDFCBalance.setText(avalebalBalanceInHDFCAfterBuzz + "");
                    //    tv_avalebalHDFCCCBalance.setText(avalebalBalanceInHDFCCCAfterBuzz + "");
                        Toast.makeText(getBaseContext(), "Record Added in HDFC and HDFC CC Table.", Toast.LENGTH_LONG).show();
                    }
                }else if(buzzSubTypeForCon==4){

                    if(usrBuzzMoney_int > avalebalBalanceInSBI){
                        Toast.makeText(getBaseContext(), "Not Sufficent bal in HDFC", Toast.LENGTH_LONG).show();
                        defaultPos();
                    }else{
                        avalebalBalanceInHDFCAfterBuzz = avalebalBalanceInHDFC + usrBuzzMoney_int;
                        avalebalBalanceInSBIAfterBuzz = avalebalBalanceInSBI - usrBuzzMoney_int;
                        avalebalBalanceInSBI = avalebalBalanceInSBIAfterBuzz;
                        avalebalBalanceInHDFC = avalebalBalanceInHDFCAfterBuzz;
                        db.addContactInTable(new Record(usrBuzzDate, usrBuzzMoney_int, 0, "HDFC -> SBI", avalebalBalanceInSBI),TABLE_SBI);
                        db.addContactInTable(new Record(usrBuzzDate, usrBuzzMoney_int, 1, "HDFC -> SBI", avalebalBalanceInHDFC),TABLE_HDFC);
                   //     tv_avalebalSBIBalance.setText(avalebalBalanceInSBIAfterBuzz + "");
                   //     tv_avalebalHDFCBalance.setText(avalebalBalanceInHDFCAfterBuzz + "");
                        Toast.makeText(getBaseContext(), "Record Added in HDFC and SBI Table.", Toast.LENGTH_LONG).show();
                    }

                }else if(buzzSubTypeForCon==5){

                    if(usrBuzzMoney_int > avalebalBalanceInHDFC){
                        Toast.makeText(getBaseContext(), "Not Sufficent bal in HDFC", Toast.LENGTH_LONG).show();
                        defaultPos();
                    }else{
                        avalebalBalanceInSBIAfterBuzz = avalebalBalanceInSBI + usrBuzzMoney_int;
                        avalebalBalanceInHDFCAfterBuzz = avalebalBalanceInHDFC - usrBuzzMoney_int;
                        avalebalBalanceInSBI = avalebalBalanceInSBIAfterBuzz;
                        avalebalBalanceInHDFC = avalebalBalanceInHDFCAfterBuzz;
                        db.addContactInTable(new Record(usrBuzzDate, usrBuzzMoney_int, 1, "SBI -> HDFC", avalebalBalanceInSBI),TABLE_SBI);
                        db.addContactInTable(new Record(usrBuzzDate, usrBuzzMoney_int, 0, "SBI -> HDFC", avalebalBalanceInHDFC),TABLE_HDFC);
                    //    tv_avalebalSBIBalance.setText(avalebalBalanceInSBIAfterBuzz + "");
                    //    tv_avalebalHDFCBalance.setText(avalebalBalanceInHDFCAfterBuzz + "");
                        Toast.makeText(getBaseContext(), "Record Added in HDFC and SBI Table.", Toast.LENGTH_LONG).show();
                    }
                }else if(buzzSubTypeForCon==6){

                    if(usrBuzzMoney_int > avalebalBalanceInSBI){
                        Toast.makeText(getBaseContext(), "Not Sufficent bal in SBI", Toast.LENGTH_LONG).show();
                        defaultPos();
                    }else{
                        avalebalBalanceInHDFCCCAfterBuzz = avalebalBalanceInHDFCCC + usrBuzzMoney_int;
                        avalebalBalanceInSBIAfterBuzz = avalebalBalanceInSBI - usrBuzzMoney_int;
                        avalebalBalanceInSBI = avalebalBalanceInSBIAfterBuzz;
                        avalebalBalanceInHDFCCC = avalebalBalanceInHDFCCCAfterBuzz;
                        db.addContactInTable(new Record(usrBuzzDate, usrBuzzMoney_int, 1, "SBI -> HDFCCC", avalebalBalanceInHDFCCC),TABLE_HDFC_CC);
                        db.addContactInTable(new Record(usrBuzzDate, usrBuzzMoney_int, 0, "SBI -> HDFCCC", avalebalBalanceInSBI),TABLE_SBI);
                        //    tv_avalebalSBIBalance.setText(avalebalBalanceInSBIAfterBuzz + "");
                        //    tv_avalebalHDFCBalance.setText(avalebalBalanceInHDFCAfterBuzz + "");
                        Toast.makeText(getBaseContext(), "Record Added in SBI and HDFCCC Table.", Toast.LENGTH_LONG).show();
                    }
                }else if(buzzSubTypeForCon==7){

                    if(usrBuzzMoney_int > avalebalBalanceInCash){
                        Toast.makeText(getBaseContext(), "Not Sufficent bal in Record", Toast.LENGTH_LONG).show();
                        defaultPos();
                    }else{
                        avalebalBalanceInHDFCCCAfterBuzz = avalebalBalanceInHDFCCC + usrBuzzMoney_int;
                        avalebalBalanceInCashAfterBuzz = avalebalBalanceInCash - usrBuzzMoney_int;
                        avalebalBalanceInCash = avalebalBalanceInCashAfterBuzz;
                        avalebalBalanceInHDFCCC = avalebalBalanceInHDFCCCAfterBuzz;
                        db.addContactInTable(new Record(usrBuzzDate, usrBuzzMoney_int, 1, "CASH -> HDFCCC", avalebalBalanceInHDFCCC),TABLE_HDFC_CC);
                        db.addContactInTable(new Record(usrBuzzDate, usrBuzzMoney_int, 0, "CASH -> HDFCCC", avalebalBalanceInCash), TABLE_CASH);
                            tv_avalebalCashBalance.setText(avalebalBalanceInCashAfterBuzz + "");
                        //    tv_avalebalHDFCBalance.setText(avalebalBalanceInHDFCAfterBuzz + "");
                        Toast.makeText(getBaseContext(), "Record Added in CASH and HDFCCC Table.", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    msg = msg +"3f,";
                }
            }else{
                msg = msg +"allf";
            }
            defaultPos();

        }
    }

    public void showRecordActivity(View view){
        Intent thirdActivityIntent = new Intent(this,ShowRecordActivity.class);

        startActivityForResult(thirdActivityIntent,4);
    }

    public void showCashTable(View view){
        Intent secondActivityIntent = new Intent(this,ShowRecordInTableActivity.class);
        secondActivityIntent.putExtra("Table", TABLE_CASH);
        startActivity(secondActivityIntent);
    }

    public void showSBITable(View view){
        Intent secondActivityIntent = new Intent(this,ShowRecordInTableActivity.class);
        secondActivityIntent.putExtra("Table",TABLE_SBI);
        startActivity(secondActivityIntent);
    }

    public void showHDFCTable(View view){
        Intent secondActivityIntent = new Intent(this,ShowRecordInTableActivity.class);
        secondActivityIntent.putExtra("Table",TABLE_HDFC);
        startActivity(secondActivityIntent);
    }

    public void showHDFCCCTable(View view){
        Intent secondActivityIntent = new Intent(this,ShowRecordInTableActivity.class);
        secondActivityIntent.putExtra("Table",TABLE_HDFC_CC);
        startActivity(secondActivityIntent);
    }

     void defaultPos(){
         et_buzzMoney.setText("");
         et_buzzCause.setText("");
         spinner_buzzType.setAdapter(adapter);
         //rbo_debit.setChecked(true);
         et_buzzMoney.requestFocus();
         InputMethodManager inputManager = (InputMethodManager)
                 getSystemService(Context.INPUT_METHOD_SERVICE);

         inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                 InputMethodManager.HIDE_NOT_ALWAYS);
         //Toast.makeText(getApplicationContext(), "called.../", Toast.LENGTH_SHORT).show();
     }

    public void deleteAll(View view){
        db.deleteAllRecordAllTable();
        tv_avalebalCashBalance.setText("0");
        Toast.makeText(getApplicationContext(), "Database Cleared.../", Toast.LENGTH_SHORT).show();
    }

    private boolean isFirstTime() {
        if (firstTime == null) {
            SharedPreferences mPreferences = this.getSharedPreferences("first_time", Context.MODE_PRIVATE);
            firstTime = mPreferences.getBoolean("firstTime", true);
            if (firstTime) {
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putBoolean("firstTime", false);
                editor.commit();
            }
        }
        return firstTime;
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            int a = s.toString().length();
            char c = 'a';
            if(a>0){
               c = s.toString().charAt(a-1);

            if(c == '\n'){
                String n = s.toString();
                String p = n.substring(0,a-1);
                et_buzzCause.setText(p);
             //
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);


               btnSaveRecord.requestFocus();
            }}
        }

        public void afterTextChanged(Editable s) {
        }
    };

}
