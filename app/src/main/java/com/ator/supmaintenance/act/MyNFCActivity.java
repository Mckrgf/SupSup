package com.ator.supmaintenance.act;


import android.content.Intent;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.ator.supmaintenance.R;
import com.ator.supmaintenance.item.DataTransform;
import com.ator.supmaintenance.item.Employee;
import com.ator.supmaintenance.item.InspectionHelp;
import com.ator.supmaintenance.item.InspectionUtil;
import com.ator.supmaintenance.item.NFCCard;
import com.ator.supmaintenance.item.NFCCardMrg;
import com.ator.supmaintenance.item.RtEnv;
import com.ator.supmaintenance.item.UserUtil;

import java.nio.charset.Charset;
import java.util.Locale;

public class MyNFCActivity extends BaseActivity implements View.OnClickListener {

    //读取出来的id
    private String mId;

    @Override
    protected int initContentView() {
        return R.layout.activity_nfc;
    }

    @Override
    protected void getIntentData() {
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        findViewById(R.id.ll_psd_login).setOnClickListener(this);
        findViewById(R.id.ll_scan_login).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }

    public void onPause() {
        super.onPause();
        //mNfcAdapter.disableForegroundDispatch(this);

    }

    public void onResume() {
        super.onResume();

    }

    @Override
    protected void start() {
        String action = getIntent().getAction();
        try {
            if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                    || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                    || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
                //处理该intent
                processIntent(getIntent());
                ParseID();
            }
         } catch (Exception e) {

        }
    }

    private void ParseID(){

        NFCCard card = NFCCardMrg.getInstance().findCard(mId);

        if (card == null){
            alertText("提示", "未知卡片,请先在NFC卡片管理中添加");
            return;
        }

        if (card.datatype == 2) {       //user
            UserUtil.getInstance().mCurEmployee = (Employee)card.carddata;
            Toast.makeText(this, "登录成功,"+UserUtil.getInstance().mCurEmployee.name, Toast.LENGTH_SHORT).show();
            RtEnv.startActivity(FirstActivity.class);
            finish();
            return;

        }else if(card.datatype == 1) {
            InspectionHelp help = InspectionUtil.getInstance().FindLabel(mId);
            if (help.isExist()){
                InspectionUtil.getInstance().SetCurrent(help);
                RtEnv.startActivity(CheckListsActivity.class);
                finish();
            }

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String action = intent.getAction();
        try {
            if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                    || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                    || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
                //处理该intent
                processIntent(intent);
                ParseID();
            }
        } catch (Exception e) {

        }
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    private String processIntent(Intent intent) {
        //取出封装在intent中的TAG
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        byte[] uidBytes = tagFromIntent.getId();
        mId = DataTransform.bytesToHexString(uidBytes);

        return mId;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_scan_login:
                RtEnv.startActivity(QRCodeActivity.class);
                break;
            case R.id.ll_psd_login:
                RtEnv.startActivity(LoginActivity.class);
                break;
        }
    }


    private NdefRecord newTextRecord(String text, Locale locale, boolean encodeInUtf8) {
        byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));

        Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16");
        byte[] textBytes = text.getBytes(utfEncoding);

        int utfBit = encodeInUtf8 ? 0 : (1 << 7);
        char status = (char) (utfBit + langBytes.length);

        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte) status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);

        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], data);
    }


    @Override
    protected void onPostSucc(){

    }

    @Override
    protected void onPostFaile(){
    }

    @Override
    protected void onGetSucc(){

    }

    @Override
    protected void onGetFaile(){

    }

}
