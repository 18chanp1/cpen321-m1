#!/bin/bash
sudo iptables -t nat -I PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 3000
sudo iptables -I INPUT -p tcp --dport 3000 -j ACCEPT

sudo iptables -t nat -I PREROUTING -p tcp --dport 443 -j REDIRECT --to-port 3443
sudo iptables -I INPUT -p tcp --dport 3443 -j ACCEPT