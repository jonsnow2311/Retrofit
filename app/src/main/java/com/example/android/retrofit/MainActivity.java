package com.example.android.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private JsonPlaceholderApi jsonPlaceholderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.somaku.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);

        //getPosts();

        //getComments();
        //createPost();
        //updatePost();
        //deletePost();
    }

    public void getPosts()
    {

        Map<String , String> parameters = new HashMap<>();
        parameters.put("userId" , "3");
        parameters.put("_sort" , "id");
        parameters.put("_order" , "desc");
        Call<List<Post>> call = jsonPlaceholderApi.getPosts(parameters);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if(!response.isSuccessful())
                {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<Post> posts = response.body();

                for( Post post: posts)
                {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n";

                    textViewResult.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

                textViewResult.setText(t.getMessage());

            }
        });
    }

    public void getComments()
    {
        Call<List<Comment>> call = jsonPlaceholderApi.getComments(2);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                if(!response.isSuccessful())
                {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<Comment> comments = response.body();

                for(Comment comment:comments)
                {
                    String content = "";
                    content +="Post ID: " + comment.getPostId() + "\n";
                    content +="ID: " + comment.getId() + "\n";
                    content +="Name: " + comment.getName() + "\n";
                    content +="Email: " + comment.getEmail() + "\n";
                    content +="Text: " + comment.getText() + "\n";

                    textViewResult.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

                textViewResult.setText(t.getMessage());

            }
        });
    }

    private void createPost()
    {
        final Post post = new Post(23 , "New Title" , "New Text");
        Call<Post> call = jsonPlaceholderApi.createPost(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(!response.isSuccessful())
                {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n";

                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

                textViewResult.setText(t.getMessage());

            }
        });
    }

    private void updatePost()
    {
        final Post post = new Post(25 , "Update Title" , "");
        Call<Post> call = jsonPlaceholderApi.putPost(5 , post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(!response.isSuccessful())
                {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "ID: " + postResponse.getId() + "\n";
                content += "User ID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getText() + "\n";

                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

                textViewResult.setText(t.getMessage());

            }
        });
    }

    private void deletePost()
    {
        Call<Void> call = jsonPlaceholderApi.deletePost(5);

        call.enqueue((new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                textViewResult.setText("Code: "+response.code());

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textViewResult.setText(t.getMessage());

            }
        }));
    }
}
