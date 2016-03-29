/*
 * Utils.cpp
 *
 *  Created on: Mar 28, 2016
 *      Author: jai1
 */

// User defined headers need a .h
#include <Utils.h>

Utils::Utils() {
	// TODO Auto-generated constructor stub

}

Utils::~Utils() {
	// TODO Auto-generated destructor stub
}

long Utils::square(int x) {
	return x * x;
}

// static needs to be declared only in header file
long Utils::cube(int x) {
	return x * x * x;
}

