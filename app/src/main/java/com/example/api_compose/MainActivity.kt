package com.example.api_compose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.api_compose.Model.User
import com.example.api_compose.State.UserState
import com.example.api_compose.ViewModel.UserViewModel
import com.example.api_compose.ui.theme.Api_composeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Api_composeTheme {
                val viewModel : UserViewModel by viewModels()
                val context = LocalContext.current
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val userState by viewModel.userState.collectAsState(initial = UserState.StartState)
                    when (userState) {
                        is UserState.LoadingState -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        is UserState.Success -> {
                            val user = (userState as UserState.Success).users
                            UserListView(user)
                        }
                        is UserState.Error -> {
                            val message = (userState as UserState.Error).errorMessage
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun UserListView(users: List<User>) {
        LazyColumn {
            items(users) { user ->
                UserItem(user = user)
            }
        }
    }


    @Composable
    fun UserItem(user: User) {
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.Black, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = user.name.substring(0, 1),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }

            Column(
                modifier = Modifier.padding(start = 6.dp),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = user.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.padding(top = 4.dp))
                Text(
                    text = user.email,
                    fontSize = 16.sp,
                    color = Color.Black,
                )
            }
        }
    }
}
