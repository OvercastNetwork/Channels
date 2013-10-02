#!/bin/bash

# Clone sk89q-command-framework
git clone https://github.com/rmsy/sk89q-command-framework
cd sk89q-command-framework
git checkout depend-fix
mvn install -DskipTests=true
