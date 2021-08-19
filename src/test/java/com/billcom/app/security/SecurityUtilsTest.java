package com.billcom.app.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.billcom.app.entity.UserApp;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Security Utils")

 class SecurityUtilsTest {

	@Mock
	SecurityUtils securityUtils;

	@Mock
	UserApp user;

	@BeforeEach
	void init() {
		given(securityUtils.getLoggedUser()).willReturn(user);
	}
	@Test
	void should_return_user_when_Userlogged() {
		// given
		UserApp user = new UserApp();
		given(securityUtils.getLoggedUser()).willReturn(user);
		// when & then
		Assertions.assertThat(securityUtils.getLoggedUser()).isNotNull();
	}

	@Test
	void checkUserRole_should_be_called() {
		UserApp user = new UserApp();
		 when(securityUtils.checkUserRole(user)).thenReturn(true);
		 
		   boolean check =securityUtils.checkUserRole(user);
	     assertTrue(check);

	}
	
	
}
