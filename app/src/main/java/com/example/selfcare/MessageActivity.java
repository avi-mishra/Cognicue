package com.example.selfcare;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String timestamp ;
    private String anonymousId;
    private ArrayList<Chat> chatList;
    private MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        mAuth = FirebaseAuth.getInstance();
        CardView card = findViewById(R.id.card);
        card.setVisibility(View.VISIBLE);
        timestamp=String.valueOf(System.currentTimeMillis());

        EditText message = findViewById(R.id.message);
        ImageView send =findViewById(R.id.send);

        RecyclerView recyclerView =findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        chatList=new ArrayList<>();
        adapter= new MessageAdapter(this,chatList);
        recyclerView.setAdapter(adapter);





        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            User me = new User(mAuth.getUid(),"online");
                            FirebaseDatabase.getInstance().getReference("User").child(mAuth.getUid()).setValue(me);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(MessageActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        FirebaseDatabase.getInstance().getReference("User").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    User anonymous = postSnapshot.getValue(User.class);
                    if(anonymous.getSeen().equals("online")&&!anonymous.getName().equals(mAuth.getUid()))
                    {
                        card.setVisibility(View.GONE);
                        anonymousId=anonymous.getName();
                        break;
                    }
                }

                if(anonymousId!=null)
                {

                    FirebaseDatabase.getInstance().getReference("Messages").child(mAuth.getUid()+anonymousId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            Log.i("ritik","kel "+snapshot);
                            chatList.clear();
                            for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                                Chat chat = postSnapshot.getValue(Chat.class);
                                chatList.add(chat);
                                Log.i("ritik","kel "+chat.getMessage());
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(message.getText()!=null)
                            {
                          DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Messages").child(mAuth.getUid()+anonymousId);
                              String key = ref.push().getKey();
                              Chat chat= new Chat(message.getText().toString().trim(), mAuth.getUid());
                              ref.child(key).setValue(chat);
                              FirebaseDatabase.getInstance().getReference("Messages").child(anonymousId+mAuth.getUid()).child(key).setValue(chat);
                                message.setText("");

                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    @Override
    protected void onDestroy() {
        User me =null;
        if(mAuth!=null)
        {      me=new User(mAuth.getUid(),"offline");
        FirebaseDatabase.getInstance().getReference("User").child(mAuth.getUid()).setValue(me);}
        FirebaseDatabase.getInstance().getReference("Messages").child(anonymousId+mAuth.getUid()).removeValue();
        FirebaseDatabase.getInstance().getReference("Messages").child(mAuth.getUid()+anonymousId).removeValue();


        super.onDestroy();
    }
}