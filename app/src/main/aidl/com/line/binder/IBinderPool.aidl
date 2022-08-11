// IBiderPool.aidl
package com.line.binder;

// Declare any non-default types here with import statements

interface IBinderPool {
   IBinder queryBinder(int binderCode);
}