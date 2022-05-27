package beyzagobel.depo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";
    private TextInputLayout ePostaWrapper, sifreWrapper;
    private Button btnGirisYap, btnKayitOl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        setTheme(R.style.Theme_Depo);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initCompanents();
        registerEventHandlers();
    }

    private void registerEventHandlers() {
        btnKayitOl_onClick();
        btnGirisYap_onClick();
    }
    private boolean sifreKontrol(TextInputLayout sifreWrapper) {
        String sifre = this.sifreWrapper.getEditText().getText().toString();

        if (sifre.length() < 6) {
            sifreWrapper.setError("Parolanız en az 6 haneli olmalıdır!");
            return false;
        }
        sifreWrapper.setError(null);
        return true;
    }
    private boolean epostaKontrol(TextInputLayout ePostaWrapper) {

        String eposta = ePostaWrapper.getEditText().getText().toString();
        if (eposta.equals("")) {
            ePostaWrapper.setError("lütfen epostanızı giriniz!");
            return false;
        }
        else{
            ePostaWrapper.setError(null);
            return true;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Toast.makeText(MainActivity.this, user.getEmail() + " giriş yaptı", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, AnasayfaActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
        else{
            Toast.makeText(MainActivity.this,  "Hoşgeldiniz!", Toast.LENGTH_SHORT).show();

        }
    }
    private void btnGirisYap_onClick() {
        btnGirisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eposta = ePostaWrapper.getEditText().getText().toString();
                String sifre = sifreWrapper.getEditText().getText().toString();

                boolean sifreKontrol = sifreKontrol(sifreWrapper);
                boolean epostaKontrol = epostaKontrol(ePostaWrapper);

                if (sifreKontrol && epostaKontrol) {
                    String email = eposta.trim();
                    mAuth.signInWithEmailAndPassword(email, sifre)
                            .addOnCompleteListener(MainActivity.this, new
                                    OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                FirebaseUser user = mAuth.getCurrentUser();
                                                Toast.makeText(MainActivity.this, email + " Başarılı Giriş Yapıldı!", Toast.LENGTH_SHORT).show();
                                                //  updateUI(user);
                                                Intent intent = new Intent(MainActivity.this, AnasayfaActivity.class);
                                                intent.putExtra("user", user);
                                                startActivity(intent);
                                            } else {
                                                Log.w(TAG, "Giriş başarısız: ", task.getException());
                                                Toast.makeText(MainActivity.this, "Bilgileri Kontrol Ediniz!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                }
                else{
                    Toast.makeText(MainActivity.this, "Bilgileri Kontrol Ediniz!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void btnKayitOl_onClick() {
        btnKayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eposta = ePostaWrapper.getEditText().getText().toString();
                String sifre = sifreWrapper.getEditText().getText().toString();

                boolean sifreKontrol = sifreKontrol(sifreWrapper);
                boolean epostaKontrol = epostaKontrol(ePostaWrapper);

                if (sifreKontrol && epostaKontrol) {
                    String email = eposta.trim();
                    mAuth.createUserWithEmailAndPassword(email, sifre)
                            .addOnCompleteListener(MainActivity.this, new
                                    OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                // Log.d(TAG, "createUserWithEmail:success");
                                                FirebaseUser user = mAuth.getCurrentUser();
                                                Toast.makeText(MainActivity.this, eposta + " Kayit Başarılı!", Toast.LENGTH_SHORT).show();
                                                updateUI(user);
                                                Intent intent = new Intent(MainActivity.this, AnasayfaActivity.class);
                                                intent.putExtra("user",user.getEmail());
                                                startActivity(intent);
                                            } else {
                                                Log.w(TAG, "Kayıt başarısız: ", task.getException());
                                                Toast.makeText(MainActivity.this, "Bilgileri Kontrol Ediniz!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                }
                else{
                    Toast.makeText(MainActivity.this, "Bilgileri Kontrol Ediniz!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void initCompanents() {
        mAuth = FirebaseAuth.getInstance();
        btnGirisYap = findViewById(R.id.btnGirisYap);
        btnKayitOl = findViewById(R.id.btnKayitOl);
        ePostaWrapper = findViewById(R.id.ePostaWrapper);
        sifreWrapper = findViewById(R.id.sifreWrapper);
    }
}