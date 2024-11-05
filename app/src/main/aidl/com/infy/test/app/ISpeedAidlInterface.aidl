package com.infy.test.app;

// ISpeedAidlInterface.aidl
// This AIDL file defines an interface for communication between a service and client[Activity]
// to share car speed data across different processes.
interface ISpeedAidlInterface {
    int getCarSpeed();
}