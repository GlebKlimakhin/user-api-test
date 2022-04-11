//package com.example.adminservice;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@FieldDefaults(level = AccessLevel.PRIVATE)
//class AdminServiceApplicationTests {

//    @Mock
//    RestTemplate restTemplate;
//
//    @InjectMocks
//    AdminService adminService;
//
//
//    @BeforeAll
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//    }

//    @Test
//    public void givenMockingIsDoneByMockito_whenGetIsCalled_shouldReturnMockedObject() {
//        List<UserDto> users = new ArrayList<>(List.of(
//                new UserDto(1L, "testUsername1", "testPassword1", List.of(new RoleDto(1, "ROLE_ADMIN"))),
//                new UserDto(2L, "testUsername2", "testPassword2", List.of(new RoleDto(1, "ROLE_ADMIN"))
//                )));
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Authorization", "test");
//        HttpEntity<String> httpEntity = new HttpEntity(httpHeaders);
//        Mockito
//                .when(restTemplate.exchange(
//                        "http://localhost:8080/api/v1/users/",
//                        HttpMethod.GET,
//                        httpEntity,
//                        List.class))
//        .thenReturn(new ResponseEntity(users, HttpStatus.OK));
//
//    }
//}
//
//}
