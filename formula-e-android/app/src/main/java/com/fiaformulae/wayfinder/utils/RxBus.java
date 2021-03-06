package com.fiaformulae.wayfinder.utils;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class RxBus {
  private final Subject<Object, Object> mBus = new SerializedSubject<>(PublishSubject.create());

  public void send() {
    mBus.onNext(new Object());
  }

  public Observable<Object> toObservable() {
    return mBus;
  }
}
