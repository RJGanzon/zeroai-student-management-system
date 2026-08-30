package com.week9.study.mapper;

public interface Mapper<A, B> {
    B mapTo(A a);
    A mapFrom(B b);

    //Update Entity with given DTO
    A updateEntity(B b, A a);
}
