CC=g++
CFLAGS=-D_GLIBCXX_USE_NANOSLEEP -Wall -std=c++0x -c
LDFLAGS= -L/usr/local/lib/ -lboost_system-mt -lboost_thread-mt -lboost_date_time-mt -lboost_regex-mt
SOURCES=RateLimiter.cc RateLimiterTest.cc 
OBJECTS=$(SOURCES:.cc=.o)
EXECUTABLE=RateLimiterTest

all: $(SOURCES) $(EXECUTABLE)
    
$(EXECUTABLE): $(OBJECTS) 
	$(CC) $(LDFLAGS) $(OBJECTS) -o $@

.cc.o:
	$(CC) $(CFLAGS) $< -o $@
