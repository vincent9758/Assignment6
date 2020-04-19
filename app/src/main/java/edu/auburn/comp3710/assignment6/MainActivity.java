package edu.auburn.comp3710.assignment6;


import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper Database;
    TextView balance;
    EditText day,month,year;
    EditText amount;
    EditText event;
    Button btnAdd;
    Button btnSub;
    TableLayout history;
    EditText Sdate,Edate;
    EditText LowBoundPrice, HighBoundPrice;
    Button search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Database = new DatabaseHelper(this);

        balance = findViewById(R.id.balance);
        day =  findViewById(R.id.day);
        month =findViewById(R.id.month);
        year = findViewById(R.id.year);
        amount = findViewById(R.id.editAmount);
        event = findViewById(R.id.editEvent);
        btnAdd =  findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);
        history =  findViewById(R.id.table);
        Sdate = findViewById(R.id.Sdate);
        Edate = findViewById(R.id.Edate);
        LowBoundPrice = findViewById(R.id.Sprice);
        HighBoundPrice = findViewById(R.id.Eprice);
        search = findViewById(R.id.search);
        AddEvent();
        GetHistory();
    }

    public void AddEvent(){
        btnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double Amount = Double.parseDouble(amount.getText().toString());
                        int date = Integer.parseInt(month.getText().toString() + day.getText().toString() + year.getText().toString());
                        Database.Create(date, Amount, event.getText().toString());
                        GetHistory();
                        reset();
                    }
                }
        );

        btnSub.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double Amount = Double.parseDouble(amount.getText().toString());
                        int date = Integer.parseInt(month.getText().toString() + day.getText().toString() + year.getText().toString());
                        Database.Create(date, Amount, event.getText().toString());
                        GetHistory();
                        reset();
                    }
                }
        );

        search.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String getSdate,getEdate,getLowBound,getHighBound;
                        getSdate = Sdate.getText().toString();
                        getEdate = Edate.getText().toString();
                        getLowBound = LowBoundPrice.getText().toString();
                        getHighBound = HighBoundPrice.getText().toString();
                        if (getLowBound.equals("") && getLowBound.equals("") && getSdate.length() > 0 && getEdate.length() > 0) {
                            search(getSdate,getEdate,"n","n");
                        }
                        else if (getSdate.equals("") && getEdate.equals("") && getLowBound.length() > 0 && getHighBound.length() > 0) {
                            search("n","n",getLowBound,getHighBound);
                        }
                        else if (getSdate.equals("") && getEdate.equals("") && getLowBound.length() >0 && getHighBound.equals("")) {
                            getHighBound = "1000000";
                            search("n","n",getLowBound,getHighBound);
                        }
                        else if (getLowBound.equals("") && getHighBound.equals("") && getEdate.equals("") && getSdate.length() > 0) {
                            getHighBound = "1000000";
                            search(getSdate,getEdate,"n","n");
                        }

                        else if (getEdate.length() > 0 && getSdate.length() > 0 && getLowBound.length() > 0 && getHighBound.length() > 0) {
                            search(getSdate,getEdate,getLowBound,getHighBound);
                        }

                        else if (getEdate.equals("") && getSdate.length() > 0 && getHighBound.length() > 0 && getLowBound.length() > 0) {
                            getHighBound = "1000000";
                            search(getSdate,getEdate,getLowBound,getHighBound);
                        }

                        else if (getEdate.length() > 0 && getSdate.length() > 0 && getHighBound.equals("") && getLowBound.length() > 0) {
                            getHighBound = "1000000";
                            search(getSdate,getEdate,getLowBound,getHighBound);
                        }
                        reset();

                    }
                }
        );

    }

    public void GetHistory(){
        Cursor result = Database.ReadData();
        Double balance = 0.0;

        if (history.getChildCount() > 0) {
            history.removeAllViews();

        }

        while(result.moveToNext()){
            double price = Double.parseDouble(result.getString(2));
            balance += price;
            TableRow t = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams();
            lp.weight = 1;


            TextView Date = new TextView(this);
            Date.setLayoutParams(lp);
            String getDate = result.getString(1);
            String sTime = getDate.substring(0,2) + "/" + getDate.substring(2,4) + "/" + getDate.substring(4,8);
            Date.setText(sTime);
            t.addView(Date);

            TextView amount = new TextView(this);
            amount.setLayoutParams(lp);
            amount.setText(result.getString(2));
            t.addView(amount);

            TextView categroy = new TextView(this);
            categroy.setLayoutParams(lp);
            categroy.setText(result.getString(3));
            t.addView(categroy);

            history.addView(t, new TableLayout.LayoutParams());

        }
        MainActivity.this.balance.setText("Current Balance: $" + Double.toString(balance));
    }

    public void search(String Sdate,String Edate,String LowBoundPrice,String HighBoundPrice){
        Double balance = 0.0;
        Cursor data = Database.SearchData(Sdate, Edate, LowBoundPrice, HighBoundPrice);

        if (history.getChildCount() > 0) {
            history.removeAllViews();

        }

        while(data.moveToNext()){
            double price = Double.parseDouble(data.getString(2));
            balance += price;
            TableRow t = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams();
            lp.weight = 1;

            TextView Date = new TextView(this);
            Date.setLayoutParams(lp);
            String getDate = data.getString(1);
            String sTime = getDate.substring(0,2) + "/" + getDate.substring(2,4) + "/" + getDate.substring(4,8);
            Date.setText(sTime);
            t.addView(Date);

            TextView amount = new TextView(this);
            amount.setLayoutParams(lp);
            amount.setText(data.getString(2));
            t.addView(amount);

            TextView categroy = new TextView(this);
            categroy.setLayoutParams(lp);
            categroy.setText(data.getString(3));
            t.addView(categroy);

            history.addView(t, new TableLayout.LayoutParams());

        }
        MainActivity.this.balance.setText("Current Balance: $" + Double.toString(balance));
    }

    public void reset(){
        MainActivity.this.amount.setText("");
        MainActivity.this.event.setText("");
        MainActivity.this.Sdate.setText("");
        MainActivity.this.Edate.setText("");
        MainActivity.this.LowBoundPrice.setText("");
        MainActivity.this.HighBoundPrice.setText("");
        MainActivity.this.day.setText("");
        MainActivity.this.month.setText("");
        MainActivity.this.year.setText("");

    }
}
