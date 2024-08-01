package com.cashrich.cashrichapp.repo;

import com.cashrich.cashrichapp.model.TokenType;

public class AuthResponseDto {
	
    
    private String accessToken;
    
    
    private int accessTokenExpiry;

    
    private TokenType tokenType;
    
   
    private String userName;
    
    
    private String userRole;
    
    

    // Private constructor to prevent direct instantiation
    private AuthResponseDto() {}

    // Manual Builder class
    public static class Builder {
        private String accessToken;
        private int accessTokenExpiry;
        private TokenType tokenType;
        private String userName;
        private String userRole;

        public Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder accessTokenExpiry(int accessTokenExpiry) {
            this.accessTokenExpiry = accessTokenExpiry;
            return this;
        }

        public Builder tokenType(TokenType tokenType) {
            this.tokenType = tokenType;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }
        
        public Builder userRole(String userRole) {
        	this.userRole=userRole;
        	return this;
        }

        public AuthResponseDto build() {
            AuthResponseDto authResponseDto = new AuthResponseDto();
            authResponseDto.accessToken = this.accessToken;
            authResponseDto.accessTokenExpiry = this.accessTokenExpiry;
            authResponseDto.tokenType = this.tokenType;
            authResponseDto.userName = this.userName;
            authResponseDto.userRole=this.userRole;
            return authResponseDto;
        }
    }

    // Getters for the fields
    public String getAccessToken() {
        return accessToken;
    }

    public int getAccessTokenExpiry() {
        return accessTokenExpiry;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getUserName() {
        return userName;
    }

	public String getUserRole() {
		return userRole;
	}

   
}
