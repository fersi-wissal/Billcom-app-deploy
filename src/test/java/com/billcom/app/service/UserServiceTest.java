package com.billcom.app.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.billcom.app.dto.UserDto;
import com.billcom.app.entity.Role;
import com.billcom.app.entity.UserApp;
import com.billcom.app.exception.NotFoundException;
import com.billcom.app.repository.UserRepository;
import com.billcom.app.security.SecurityUtils;


@ExtendWith(MockitoExtension.class)
@DisplayName("User Service Test")
class UserServiceTest {

	@InjectMocks
	UserService userService;
	@Mock
	UserApp user;
	@Mock
	UserDto userDto;
	@Mock
	SecurityUtils securityUtils;
	@Mock
	UserRepository userRepository;
	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@BeforeEach
	void init() {
		given(userService.getCurrentUser()).willReturn(user);
	}

	/** Test get CurrentUser */
	@Test
	void should_return_user_when_user_is_logged() {
		// given
		UserApp user = new UserApp();
		given(userService.getCurrentUser()).willReturn(user);
		// when & then
		Assertions.assertThat(userService.getCurrentUser()).isNotNull();
		Assertions.assertThat(userService.getCurrentUser().getEmail()).isEqualTo(user.getEmail());
	}

	@Test
	void should_test_get_user_when_logged_user_is_manager() {
		// given
		final UserService userService = spy(this.userService);
		UserApp user = new UserApp(1L,"firstName","lastName",true);
		given(userService.getCurrentUser()).willReturn(user);
		Mockito.doReturn(true).when(userService).checkifUserLoggedIsManager(user);
		  //when
          Assertions.assertThatThrownBy(() ->{
        	userService.getUser(1L);
        }).isInstanceOf(NotFoundException.class);
   
        //then
		Mockito.verify(userRepository, Mockito.times(1)).findById(1L);
		}
	

	/** Test Return List Of Users **/
	@Test
	void should_return_all_users_when_logged_as_manager() {
		// given
		final UserService userService = spy(this.userService);
		Mockito.doReturn(true).when(securityUtils).checkifUserLoggedIsManager(user);
		// when
		userService.getUsers();

		Mockito.verify(userRepository, Mockito.times(1)).findAll();
	}
	}



