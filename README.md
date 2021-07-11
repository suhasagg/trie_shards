# trie_shards

Autosuggest at scale - trie sharding

Suppose you have stored prefix in different instances:

A-F
G-M
N-R
S
T-Z
if there are too many records under A-F instance, you can just re-partition and split it to A-D, E-F.
if there are too many records in S instance, you can add another load balancer in front of this instance, and split the instance to two, keeping SA-SM in one, and SN-SZ in another. For this use case you also change the config in overall load balancer so it can dispatch the traffic properly to the S-load balancer.
