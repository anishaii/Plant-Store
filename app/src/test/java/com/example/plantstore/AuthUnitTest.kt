package com.example.plantstore

import com.example.plantstore.repository.AuthRepository
import com.example.plantstore.repository.AuthRepositoryImpl
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import org.mockito.Mock
import com.google.android.gms.tasks.Task
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.verify
import junit.framework.TestCase.assertEquals

class AuthUnitTest {

        @Mock
        private lateinit var mockAuth: FirebaseAuth

        @Mock
        private lateinit var mockTask: Task<AuthResult>

        private lateinit var authRepository: AuthRepositoryImpl

        @Captor
        private lateinit var captor: ArgumentCaptor<OnCompleteListener<AuthResult>>

        @Before
        fun setup() {
            MockitoAnnotations.openMocks(this)
            authRepository = AuthRepositoryImpl(mockAuth)
        }
        @Test
        fun testLogin_Successful() {
            val email = "test@example.com"
            val password = "testPassword"
            var expectedResult = "Initial Value" // Define the initial value

            // Mocking task to simulate successful registration
            `when`(mockTask.isSuccessful).thenReturn(true)
            `when`(mockAuth.signInWithEmailAndPassword(any(), any()))
                .thenReturn(mockTask)

            // Define a callback that updates the expectedResult
            val callback = { success: Boolean, message: String? ->
                expectedResult = message ?: "Callback message is null"
            }

            // Call the function under test
            authRepository.login(email, password, callback)

            verify(mockTask).addOnCompleteListener(captor.capture())
            captor.value.onComplete(mockTask)

            // Assert the result
            assertEquals("Login successfully", expectedResult)
        }

}