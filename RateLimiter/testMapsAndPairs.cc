#include<iostream>
#include<cassert>
#include<map>
#include<utility>
#include<string>

using namespace std;

typedef pair<string, string> TwoStringPair;
typedef pair<pair<string, string>, long> KeyValuePair;

int main() {
	TwoStringPair p1 = make_pair("T1", "T2");
	TwoStringPair p2 = make_pair("T1", "T1");
	TwoStringPair p3 = make_pair("T2", "T1");
	TwoStringPair p4 = make_pair("T1", "T2");

	KeyValuePair keyValue = make_pair(p1, 10);
	map<TwoStringPair, long> m;

	m.insert(keyValue);
	assert(m.find(p1) != m.end());
	assert(m.find(p2) == m.end());
	assert(m.find(p3) == m.end());
	assert(m.find(p4) != m.end());
	return 0;
}
