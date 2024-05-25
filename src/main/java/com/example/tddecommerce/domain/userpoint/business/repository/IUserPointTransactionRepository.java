package com.example.tddecommerce.domain.userpoint.business.repository;

import com.example.tddecommerce.domain.userpoint.business.model.UserPointTransaction;

public interface IUserPointTransactionRepository  {
    void save(UserPointTransaction userPointTransaction);

}
