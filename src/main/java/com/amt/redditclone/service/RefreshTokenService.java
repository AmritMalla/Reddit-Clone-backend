package com.amt.redditclone.service;

import com.amt.redditclone.dto.RefreshTokenRequest;
import com.amt.redditclone.exceptions.SpringRedditException;
import com.amt.redditclone.model.RefreshToken;
import com.amt.redditclone.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

/**
 * Created by Amrit Malla
 * date : 04/29/2021
 * time : 5:03 PM
 */
@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    public void validateRefreshToken(RefreshTokenRequest refreshToken) {
        refreshTokenRepository.findByToken(refreshToken.getRefreshToken())
                .orElseThrow(() -> new SpringRedditException("Invalid Refresh Token"));

    }

    public void deleteRefreshToken(String token){
        refreshTokenRepository.deleteByToken(token);
    }


}
