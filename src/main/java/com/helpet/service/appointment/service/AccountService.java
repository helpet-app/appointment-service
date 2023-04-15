package com.helpet.service.appointment.service;

import com.helpet.exception.NotFoundLocalizedException;
import com.helpet.service.appointment.service.error.NotFoundLocalizedError;
import com.helpet.service.appointment.storage.model.Account;
import com.helpet.service.appointment.storage.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean accountExists(UUID accountId) {
        return accountRepository.existsById(accountId);
    }

    public Account getAccount(UUID accountId) throws NotFoundLocalizedException {
        return accountRepository.findAccountById(accountId)
                                .orElseThrow(() -> new NotFoundLocalizedException(NotFoundLocalizedError.ACCOUNT_DOES_NOT_EXIST));
    }
}
