
# A.cpp : A.o B.o
# $@ object to be built (A.cpp)
# $< 1st argument - A.o
# $^ - all arguments (space separated) - A.o B.o

#Include Directory
IDIR = ./inc

#Lib Directory
LDIR = lib

#Compiler
CC = g++

#Compile Flags
CXXFLAGS = -fprofile-arcs -ftest-coverage -O2 -g -Wall -fmessage-length=0 -I$(IDIR)

#Source Directory
SDIR = src

#Object Directory
ODIR = obj

#test directory 
TDIR = tests

#Bin Directory
BDIR = bin

#library
LIB =	libUsingGoogleTest.so

#Target
TARGET=$(BDIR)/main

$(ODIR)/%.o: $(SDIR)/%.cpp
	$(CC) -lstdc++ -fPIC $(CXXFLAGS) -c -o $@ $< 

$(LIB): $(ODIR)/Utils.o
	$(CC) -shared -o $(LDIR)/$@ $^ -lgcov 

$(TARGET): $(LIB)
	$(CC) -fprofile-arcs -ftest-coverage -o $@ $(TDIR)/main.cpp -L $(LDIR) -l UsingGoogleTest -I $(IDIR) 
	
all: $(TARGET) 

clean:
	rm -f $(ODIR)/*.o $(TARGET) $(LDIR)/*.so
