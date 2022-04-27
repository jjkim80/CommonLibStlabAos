package com.dkitec.commonlib_stalb_aos;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.dkitec.commonlib_stalb_aos.utils.HashUtils;
import com.dkitec.commonlib_stalb_aos.utils.Logger;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class EncryptionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spinner;
    private EditText inputEncryptText;
    private TextView encryptTextView;
    private Button btnEncode;
    private Button btnDecode;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption);

        spinner = findViewById(R.id.encrypt_spinner);
        inputEncryptText = findViewById(R.id.encrypt_text);
        encryptTextView = findViewById(R.id.encrypt_text_view);
        btnEncode = findViewById(R.id.btnEncode);
        btnDecode = findViewById(R.id.btnDecode);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.encrypt_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        HashUtils hashUtils = new HashUtils();
        KeyPair keyPair = null;
        try {
            keyPair = hashUtils.getRSAKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        btnEncode.setOnClickListener(view -> {
            Editable encryptText = inputEncryptText.getText();
            int selectItem = spinner.getSelectedItemPosition();
            Logger.d("selectItem :" + selectItem);
            switch (selectItem) {
                case 0:
                    encryptTextView.setText(hashUtils.getBase64Encrypt(encryptText.toString()));
                    break;
                case 1:
                    encryptTextView.setText(hashUtils.setAESEncrypt(encryptText.toString()));
                    break;
                case 2:
                    String encrypted = null;
                    try {
                        encrypted = hashUtils.encryptRSA(encryptText.toString(), publicKey);
                        encryptTextView.setText(encrypted);
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    encryptTextView.setText(hashUtils.getEncryptData(encryptText.toString()));
                    break;
                default:
                    Logger.d("해당없음");
                    break;
            }
        });

        btnDecode.setOnClickListener(view -> {
            int selectItem = spinner.getSelectedItemPosition();
            switch (selectItem) {
                case 0:
                    encryptTextView.setText(hashUtils.getBase64Decrypt(encryptTextView.getText().toString()));
                    break;
                case 1:
                    String encryptedText = (String) encryptTextView.getText();
                    encryptTextView.setText(hashUtils.setAESDecrypt(encryptedText));
                    break;

                case 2:
                    String decrypted = null;
                    try {
                        decrypted = hashUtils.decryptRSA(encryptTextView.getText().toString(), privateKey);
                        encryptTextView.setText(decrypted);
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    Toast.makeText(this, "복호화 불가능", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Logger.d("해당없음");
                    break;
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}