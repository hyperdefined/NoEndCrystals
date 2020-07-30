# NoEndCrystals
[![Downloads](https://img.shields.io/github/downloads/hyperdefined/NoEndCrystals/total?logo=github)](https://github.com/hyperdefined/NoEndCrystals/releases) [![Donate with Bitcoin](https://en.cryptobadges.io/badge/micro/1F29aNKQzci3ga5LDcHHawYzFPXvELTFoL)](https://en.cryptobadges.io/donate/1F29aNKQzci3ga5LDcHHawYzFPXvELTFoL) [![Donate with Ethereum](https://en.cryptobadges.io/badge/micro/0x0f58B66993a315dbCc102b4276298B5Ff8895F41)](https://en.cryptobadges.io/donate/0x0f58B66993a315dbCc102b4276298B5Ff8895F41)

Disable placement of End Crystals in certain worlds.

**Java 11+ is required.**

## Features
- Set custom worlds to disable placement of end crystals.
- Set custom message for when a player tries to place a crystal in a disabled world.
- `/noendcrystals` reload to reload configuration.

## Configuration
This is the default configuration.
```
disabled_worlds:
- world1
- world2
- world3
message: '&cEnd Crystals are disabled in this world.'
```
