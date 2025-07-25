package com.example.plantstore

import com.example.plantstore.repository.AuthRepositoryImpl
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertFalse

class RegisterUnitTest {

    @Mock
    private lateinit var mockAuth: FirebaseAuth

    @Mock
    private lateinit var mockTask: Task<AuthResult>

    @Mock
    private lateinit var mockFirebaseUser: FirebaseUser

    private lateinit var authRepository: AuthRepositoryImpl

    @Captor
    private lateinit var captor: ArgumentCaptor<OnCompleteListener<AuthResult>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        authRepository = AuthRepositoryImpl(mockAuth)
    }

    @Test
    fun testRegister_Successful() {
        val email = "test@example.com"
        val password = "testPassword"
        var actualMessage = ""
        var actualUid = ""
        var actualSuccess = false

        // Mocking FirebaseUser and its uid
        `when`(mockAuth.currentUser).thenReturn(mockFirebaseUser)
        `when`(mockFirebaseUser.uid).thenReturn("mockedUserId")

        // Simulate successful task
        `when`(mockTask.isSuccessful).thenReturn(true)
        `when`(mockAuth.createUserWithEmailAndPassword(anyString(), anyString())).thenReturn(mockTask)

        val callback = { success: Boolean, message: String, uid: String ->
            actualSuccess = success
            actualMessage = message
            actualUid = uid
        }

        authRepository.register(email, password, callback)

        verify(mockTask).addOnCompleteListener(captor.capture())
        captor.value.onComplete(mockTask)

        assertTrue(actualSuccess)
        assertEquals("Registered successfully", actualMessage)
        assertEquals("mockedUserId", actualUid)
    }

    @Test
    fun testRegister_Failure() {
        val email = "test@example.com"
        val password = "testPassword"
        var actualMessage = ""
        var actualUid = ""
        var actualSuccess = true

        val mockErrorMessage = "Email already in use"

        // Mock Exception for task.exception
        val mockException = mock(Exception::class.java)
        `when`(mockException.message).thenReturn(mockErrorMessage)

        // Simulate failed task with exception
        `when`(mockTask.isSuccessful).thenReturn(false)
        `when`(mockTask.exception).thenReturn(mockException)
        `when`(mockAuth.createUserWithEmailAndPassword(anyString(), anyString())).thenReturn(mockTask)

        val callback = { success: Boolean, message: String, uid: String ->
            actualSuccess = success
            actualMessage = message
            actualUid = uid
        }

        authRepository.register(email, password, callback)

        verify(mockTask).addOnCompleteListener(captor.capture())
        captor.value.onComplete(mockTask)

        assertFalse(actualSuccess)
        assertEquals(mockErrorMessage, actualMessage)
        assertEquals("", actualUid)
    }
}
