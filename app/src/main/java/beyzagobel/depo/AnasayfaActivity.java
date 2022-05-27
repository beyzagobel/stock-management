package beyzagobel.depo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AnasayfaActivity extends AppCompatActivity {

    private Intent gelenIntent;
    private TextView lblEposta;
    private FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser user;
    private Button btnDepolar, btnDepoEkle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);

        initConponents();
        registerEventHandlers();
        authStateListener();
        intentKarsila();

    }

    private void intentKarsila() {
        gelenIntent = getIntent();
        user = gelenIntent.getParcelableExtra("user");
        String email = user.getEmail();
        lblEposta.setText(email);
    }

    private void authStateListener() {
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                // Eğer geçerli bir kullanıcı oturumu yoksa LoginActivity e geçilir.
                // Oturum kapatma işlemi yapıldığında bu sayede LoginActivity e geçilir.
                if (user == null) {
                    startActivity(new Intent(AnasayfaActivity.this, MainActivity.class));
                    finish();
                }
            }
        };
    }
    private void registerEventHandlers() {
        btnDepolar_onClick();
        btnDepoEkle_onClick();
    }
    private void btnDepoEkle_onClick() {
        btnDepoEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnasayfaActivity.this,DepoEkleActivity.class);
                startActivity(intent);
            }
        });
    }
    private void btnDepolar_onClick() {
        btnDepolar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnasayfaActivity.this, DepolarActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        startActivity(new Intent(AnasayfaActivity.this, MainActivity.class));
                        break;
                }
        return super.onOptionsItemSelected(menuItem);
            }

    private void initConponents() {
        lblEposta = findViewById(R.id.lblEposta);
        btnDepoEkle = findViewById(R.id.btnDepoEkle);
        btnDepolar = findViewById(R.id.btnDepolar);
    }
}

