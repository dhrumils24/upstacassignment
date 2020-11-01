package org.upgrad.upstac.testrequests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.users.User;
import org.upgrad.upstac.users.models.Gender;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestRequestServiceTest1 {

    @InjectMocks
    TestRequestService testRequestService;

    @Mock
    TestRequestRepository testRequestRepository;

    @Test
    public void when_save_test_with_valid_parameters() {
        //Arrange
        User loggedInUser = new User();
        loggedInUser.setUserName("sometestuser");
        CreateTestRequest createTestRequest = new CreateTestRequest();
        createTestRequest.setAddress("some Addres");
        createTestRequest.setAge(98);
        createTestRequest.setEmail("someone" + "123456789" + "@somedomain.com");
        createTestRequest.setGender(Gender.MALE);
        createTestRequest.setName("someuser");
        createTestRequest.setPhoneNumber("123456789");
        createTestRequest.setPinCode(716768);

        when(testRequestRepository.findByEmailOrPhoneNumber(createTestRequest.getEmail(), createTestRequest.getPhoneNumber())).thenReturn(new ArrayList<>());

        testRequestService.createTestRequestFrom(loggedInUser, createTestRequest);

        verify(testRequestRepository, times(2)).save(any());

    }
    @Test
    public void when_save_test_with_same_parameters() {
        //Arrange
        User loggedInUser = new User();
        loggedInUser.setUserName("sometestuser");
        CreateTestRequest createTestRequest = new CreateTestRequest();
        createTestRequest.setAddress("some Addres");
        createTestRequest.setAge(98);
        createTestRequest.setEmail("someone" + "123456789" + "@somedomain.com");
        createTestRequest.setGender(Gender.MALE);
        createTestRequest.setName("someuser");
        createTestRequest.setPhoneNumber("123456789");
        createTestRequest.setPinCode(716768);

        TestRequest testRequest = new TestRequest();
        testRequest.setStatus(RequestStatus.INITIATED);
        testRequest.setEmail("someone" + "123456789" + "@somedomain.com");
        testRequest.setPhoneNumber("123456789");

        List<TestRequest> mockedTestRequests = new ArrayList<>();
        mockedTestRequests.add(testRequest);

        when(testRequestRepository.findByEmailOrPhoneNumber(createTestRequest.getEmail(), createTestRequest.getPhoneNumber())).thenReturn(mockedTestRequests);

        assertThrows(AppException.class, ()-> {
            testRequestService.createTestRequestFrom(loggedInUser, createTestRequest);
        });

    }
}