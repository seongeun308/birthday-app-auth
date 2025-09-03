package com.birthday.auth.service;

import com.birthday.auth.domain.dto.SignupDto;
import com.birthday.auth.domain.dto.TokenPair;
import com.birthday.auth.domain.entity.Account;
import com.birthday.auth.mapper.SignupDtoMapper;
import com.birthday.auth.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final SignupDtoMapper signupDtoMapper;

    @Override
    public void signup(SignupDto signupDto) {
        Account account = signupDtoMapper.toAccount(signupDto);
        accountRepository.save(account);

        // todo: 계정 생성 이벤트 발행
    }

    @Override
    public void delete(Long userId) {

    }

    @Override
    public TokenPair login(String email, String password) {
        return null;
    }

    @Override
    public void logout(Long userId) {

    }
}
