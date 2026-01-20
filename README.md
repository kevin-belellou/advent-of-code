# Advent of Code

[![GitHub License][badge_license]][file_license]
![Java version][badge_java_version]
[![Test results][badge_test_results]][actions_tests]
[![Code coverage][badge_code_coverage]][actions_tests]

Personal repository to develop
and store my answers to the incredible puzzles from [adventofcode.com][adventofcode] 🎄 🎅.

## GitHub jobs status

[![Build][badge_actions_build]][actions_build]
[![Qodana][badge_actions_qodana]][actions_qodana]
[![Update README ⭐][badge_actions_update_readme]][actions_update_readme]
[![Dependabot Updates][badge_actions_dependabot_updates]][actions_dependabot_updates]

## How it works

This repository is organized to solve Advent of Code puzzles in a structured and testable way.

### Project Structure

Each day's puzzle is represented by a separate class in a year-specific package:

- Classes are located in `src/main/java/com/belellou/kevin/advent/year{YYYY}/Day{N}.java`
- For example: `Day10.java` in the `year2018` package solves Day 10 of Advent of Code 2018

### Implementation Pattern

Every day class:

1. **Extends `AbstractDaySolver<FirstSolutionType, SecondSolutionType>`** - a generic base class where:
   - `FirstSolutionType` is the return type for Part 1's solution (e.g., `String`, `Integer`, `Long`)
   - `SecondSolutionType` is the return type for Part 2's solution

2. **Implements the `DaySolver` interface** which requires four methods:
   - `solveFirstStar()` - Computes the solution for Part 1
   - `getFirstStarSolution()` - Returns the expected answer for Part 1 (for verification)
   - `solveSecondStar()` - Computes the solution for Part 2
   - `getSecondStarSolution()` - Returns the expected answer for Part 2 (for verification)

3. **Processes input from text files** located at `src/main/resources/year{YYYY}/input-{N}.txt`

### Testing

Tests are automatically executed for each puzzle solution to verify correctness:

- Each day's solution is tested by comparing the computed results from `solveFirstStar()` and `solveSecondStar()`
  against the expected values returned by `getFirstStarSolution()` and `getSecondStarSolution()`
- Tests run against the actual puzzle input files to ensure solutions work with real data
- The test suite is executed via GitHub Actions on every commit, with results visible in the test results badge above
- Code coverage metrics are also tracked to ensure comprehensive testing of the solution implementations

Execution of the tests is done by the `DaySolverTest` class, which scans the classpath for all classes implementing the
DaySolver interface and creates dynamic tests for both the first and second star solutions.
Tests can be filtered by year and day using environment variables.

Skipped tests (annotated with `@DisableTest`) are challenges that have not yet been solved.

## All my stars

![2015 stars][badge_stars_2015]
![2016 stars][badge_stars_2016]
![2017 stars][badge_stars_2017]
![2018 stars][badge_stars_2018]

## Results by year

[//]: # (@formatter:off)

<!--- advent_readme_stars 2015 table --->
### 2015 Results

| Day | Solution | Part 1 | Part 2 |
| :---: | :---: | :---: | :---: |
| [Day 1](https://adventofcode.com/2015/day/1) | [Day1.java](src/main/java/com/belellou/kevin/advent/year2015/Day1.java) | ⭐ | ⭐ |
| [Day 2](https://adventofcode.com/2015/day/2) | [Day2.java](src/main/java/com/belellou/kevin/advent/year2015/Day2.java) | ⭐ | ⭐ |
| [Day 3](https://adventofcode.com/2015/day/3) | [Day3.java](src/main/java/com/belellou/kevin/advent/year2015/Day3.java) | ⭐ | ⭐ |
| [Day 4](https://adventofcode.com/2015/day/4) | [Day4.java](src/main/java/com/belellou/kevin/advent/year2015/Day4.java) | ⭐ | ⭐ |
| [Day 5](https://adventofcode.com/2015/day/5) | [Day5.java](src/main/java/com/belellou/kevin/advent/year2015/Day5.java) | ⭐ | ⭐ |
| [Day 6](https://adventofcode.com/2015/day/6) | [Day6.java](src/main/java/com/belellou/kevin/advent/year2015/Day6.java) | ⭐ | ⭐ |
| [Day 7](https://adventofcode.com/2015/day/7) | [Day7.java](src/main/java/com/belellou/kevin/advent/year2015/Day7.java) | ⭐ | ⭐ |
| [Day 8](https://adventofcode.com/2015/day/8) | [Day8.java](src/main/java/com/belellou/kevin/advent/year2015/Day8.java) | ⭐ |   |
| [Day 9](https://adventofcode.com/2015/day/9) | [Day9.java](src/main/java/com/belellou/kevin/advent/year2015/Day9.java) |   |   |
| [Day 10](https://adventofcode.com/2015/day/10) | [Day10.java](src/main/java/com/belellou/kevin/advent/year2015/Day10.java) | ⭐ | ⭐ |
| [Day 11](https://adventofcode.com/2015/day/11) | [Day11.java](src/main/java/com/belellou/kevin/advent/year2015/Day11.java) | ⭐ | ⭐ |
| [Day 12](https://adventofcode.com/2015/day/12) | [Day12.java](src/main/java/com/belellou/kevin/advent/year2015/Day12.java) | ⭐ | ⭐ |
| [Day 13](https://adventofcode.com/2015/day/13) | [Day13.java](src/main/java/com/belellou/kevin/advent/year2015/Day13.java) | ⭐ | ⭐ |
| [Day 14](https://adventofcode.com/2015/day/14) | [Day14.java](src/main/java/com/belellou/kevin/advent/year2015/Day14.java) | ⭐ | ⭐ |
| [Day 15](https://adventofcode.com/2015/day/15) | [Day15.java](src/main/java/com/belellou/kevin/advent/year2015/Day15.java) | ⭐ | ⭐ |
| [Day 16](https://adventofcode.com/2015/day/16) | [Day16.java](src/main/java/com/belellou/kevin/advent/year2015/Day16.java) | ⭐ | ⭐ |
| [Day 17](https://adventofcode.com/2015/day/17) | [Day17.java](src/main/java/com/belellou/kevin/advent/year2015/Day17.java) | ⭐ | ⭐ |
| [Day 18](https://adventofcode.com/2015/day/18) | [Day18.java](src/main/java/com/belellou/kevin/advent/year2015/Day18.java) | ⭐ | ⭐ |
| [Day 19](https://adventofcode.com/2015/day/19) | [Day19.java](src/main/java/com/belellou/kevin/advent/year2015/Day19.java) | ⭐ |   |
| [Day 20](https://adventofcode.com/2015/day/20) | [Day20.java](src/main/java/com/belellou/kevin/advent/year2015/Day20.java) | ⭐ | ⭐ |
| [Day 21](https://adventofcode.com/2015/day/21) | [Day21.java](src/main/java/com/belellou/kevin/advent/year2015/Day21.java) | ⭐ | ⭐ |
| [Day 22](https://adventofcode.com/2015/day/22) | [Day22.java](src/main/java/com/belellou/kevin/advent/year2015/Day22.java) |   |   |
| [Day 23](https://adventofcode.com/2015/day/23) | [Day23.java](src/main/java/com/belellou/kevin/advent/year2015/Day23.java) | ⭐ | ⭐ |
| [Day 24](https://adventofcode.com/2015/day/24) | [Day24.java](src/main/java/com/belellou/kevin/advent/year2015/Day24.java) |   |   |
| [Day 25](https://adventofcode.com/2015/day/25) | [Day25.java](src/main/java/com/belellou/kevin/advent/year2015/Day25.java) | ⭐ |   |
<!--- advent_readme_stars 2015 table --->

<!--- advent_readme_stars 2016 table --->
### 2016 Results

| Day | Solution | Part 1 | Part 2 |
| :---: | :---: | :---: | :---: |
| [Day 1](https://adventofcode.com/2016/day/1) | [Day1.java](src/main/java/com/belellou/kevin/advent/year2016/Day1.java) | ⭐ | ⭐ |
| [Day 2](https://adventofcode.com/2016/day/2) | [Day2.java](src/main/java/com/belellou/kevin/advent/year2016/Day2.java) | ⭐ | ⭐ |
| [Day 3](https://adventofcode.com/2016/day/3) | [Day3.java](src/main/java/com/belellou/kevin/advent/year2016/Day3.java) | ⭐ | ⭐ |
| [Day 4](https://adventofcode.com/2016/day/4) | [Day4.java](src/main/java/com/belellou/kevin/advent/year2016/Day4.java) | ⭐ | ⭐ |
| [Day 5](https://adventofcode.com/2016/day/5) | [Day5.java](src/main/java/com/belellou/kevin/advent/year2016/Day5.java) | ⭐ | ⭐ |
| [Day 6](https://adventofcode.com/2016/day/6) | [Day6.java](src/main/java/com/belellou/kevin/advent/year2016/Day6.java) | ⭐ | ⭐ |
| [Day 7](https://adventofcode.com/2016/day/7) | [Day7.java](src/main/java/com/belellou/kevin/advent/year2016/Day7.java) | ⭐ | ⭐ |
| [Day 8](https://adventofcode.com/2016/day/8) | [Day8.java](src/main/java/com/belellou/kevin/advent/year2016/Day8.java) | ⭐ | ⭐ |
| [Day 9](https://adventofcode.com/2016/day/9) | [Day9.java](src/main/java/com/belellou/kevin/advent/year2016/Day9.java) | ⭐ |   |
| [Day 10](https://adventofcode.com/2016/day/10) | [Day10.java](src/main/java/com/belellou/kevin/advent/year2016/Day10.java) | ⭐ | ⭐ |
| [Day 11](https://adventofcode.com/2016/day/11) |  |   |   |
| [Day 12](https://adventofcode.com/2016/day/12) | [Day12.java](src/main/java/com/belellou/kevin/advent/year2016/Day12.java) | ⭐ | ⭐ |
| [Day 13](https://adventofcode.com/2016/day/13) |  |   |   |
| [Day 14](https://adventofcode.com/2016/day/14) | [Day14.java](src/main/java/com/belellou/kevin/advent/year2016/Day14.java) | ⭐ | ⭐ |
| [Day 15](https://adventofcode.com/2016/day/15) | [Day15.java](src/main/java/com/belellou/kevin/advent/year2016/Day15.java) | ⭐ | ⭐ |
| [Day 16](https://adventofcode.com/2016/day/16) | [Day16.java](src/main/java/com/belellou/kevin/advent/year2016/Day16.java) | ⭐ | ⭐ |
| [Day 17](https://adventofcode.com/2016/day/17) | [Day17.java](src/main/java/com/belellou/kevin/advent/year2016/Day17.java) | ⭐ | ⭐ |
| [Day 18](https://adventofcode.com/2016/day/18) | [Day18.java](src/main/java/com/belellou/kevin/advent/year2016/Day18.java) | ⭐ | ⭐ |
| [Day 19](https://adventofcode.com/2016/day/19) | [Day19.java](src/main/java/com/belellou/kevin/advent/year2016/Day19.java) | ⭐ |   |
| [Day 20](https://adventofcode.com/2016/day/20) | [Day20.java](src/main/java/com/belellou/kevin/advent/year2016/Day20.java) | ⭐ | ⭐ |
| [Day 21](https://adventofcode.com/2016/day/21) | [Day21.java](src/main/java/com/belellou/kevin/advent/year2016/Day21.java) | ⭐ |   |
| [Day 22](https://adventofcode.com/2016/day/22) | [Day22.java](src/main/java/com/belellou/kevin/advent/year2016/Day22.java) | ⭐ |   |
<!--- advent_readme_stars 2016 table --->

<!--- advent_readme_stars 2017 table --->
### 2017 Results

| Day | Solution | Part 1 | Part 2 |
| :---: | :---: | :---: | :---: |
| [Day 1](https://adventofcode.com/2017/day/1) | [Day1.java](src/main/java/com/belellou/kevin/advent/year2017/Day1.java) | ⭐ | ⭐ |
| [Day 2](https://adventofcode.com/2017/day/2) | [Day2.java](src/main/java/com/belellou/kevin/advent/year2017/Day2.java) | ⭐ | ⭐ |
| [Day 3](https://adventofcode.com/2017/day/3) | [Day3.java](src/main/java/com/belellou/kevin/advent/year2017/Day3.java) | ⭐ | ⭐ |
| [Day 4](https://adventofcode.com/2017/day/4) | [Day4.java](src/main/java/com/belellou/kevin/advent/year2017/Day4.java) | ⭐ | ⭐ |
| [Day 5](https://adventofcode.com/2017/day/5) | [Day5.java](src/main/java/com/belellou/kevin/advent/year2017/Day5.java) | ⭐ | ⭐ |
| [Day 6](https://adventofcode.com/2017/day/6) | [Day6.java](src/main/java/com/belellou/kevin/advent/year2017/Day6.java) | ⭐ | ⭐ |
| [Day 7](https://adventofcode.com/2017/day/7) | [Day7.java](src/main/java/com/belellou/kevin/advent/year2017/Day7.java) | ⭐ | ⭐ |
| [Day 8](https://adventofcode.com/2017/day/8) | [Day8.java](src/main/java/com/belellou/kevin/advent/year2017/Day8.java) | ⭐ | ⭐ |
| [Day 9](https://adventofcode.com/2017/day/9) | [Day9.java](src/main/java/com/belellou/kevin/advent/year2017/Day9.java) | ⭐ | ⭐ |
| [Day 10](https://adventofcode.com/2017/day/10) | [Day10.java](src/main/java/com/belellou/kevin/advent/year2017/Day10.java) | ⭐ | ⭐ |
| [Day 11](https://adventofcode.com/2017/day/11) | [Day11.java](src/main/java/com/belellou/kevin/advent/year2017/Day11.java) | ⭐ | ⭐ |
| [Day 12](https://adventofcode.com/2017/day/12) | [Day12.java](src/main/java/com/belellou/kevin/advent/year2017/Day12.java) | ⭐ | ⭐ |
| [Day 13](https://adventofcode.com/2017/day/13) | [Day13.java](src/main/java/com/belellou/kevin/advent/year2017/Day13.java) | ⭐ | ⭐ |
| [Day 14](https://adventofcode.com/2017/day/14) | [Day14.java](src/main/java/com/belellou/kevin/advent/year2017/Day14.java) | ⭐ | ⭐ |
| [Day 15](https://adventofcode.com/2017/day/15) | [Day15.java](src/main/java/com/belellou/kevin/advent/year2017/Day15.java) | ⭐ | ⭐ |
| [Day 16](https://adventofcode.com/2017/day/16) | [Day16.java](src/main/java/com/belellou/kevin/advent/year2017/Day16.java) | ⭐ | ⭐ |
| [Day 17](https://adventofcode.com/2017/day/17) | [Day17.java](src/main/java/com/belellou/kevin/advent/year2017/Day17.java) | ⭐ | ⭐ |
| [Day 18](https://adventofcode.com/2017/day/18) | [Day18.java](src/main/java/com/belellou/kevin/advent/year2017/Day18.java) | ⭐ | ⭐ |
| [Day 19](https://adventofcode.com/2017/day/19) | [Day19.java](src/main/java/com/belellou/kevin/advent/year2017/Day19.java) | ⭐ | ⭐ |
| [Day 20](https://adventofcode.com/2017/day/20) | [Day20.java](src/main/java/com/belellou/kevin/advent/year2017/Day20.java) | ⭐ | ⭐ |
<!--- advent_readme_stars 2017 table --->

<!--- advent_readme_stars 2018 table --->
### 2018 Results

| Day | Solution | Part 1 | Part 2 |
| :---: | :---: | :---: | :---: |
| [Day 1](https://adventofcode.com/2018/day/1) | [Day1.java](src/main/java/com/belellou/kevin/advent/year2018/Day1.java) | ⭐ | ⭐ |
| [Day 2](https://adventofcode.com/2018/day/2) | [Day2.java](src/main/java/com/belellou/kevin/advent/year2018/Day2.java) | ⭐ | ⭐ |
| [Day 3](https://adventofcode.com/2018/day/3) | [Day3.java](src/main/java/com/belellou/kevin/advent/year2018/Day3.java) | ⭐ | ⭐ |
| [Day 4](https://adventofcode.com/2018/day/4) | [Day4.java](src/main/java/com/belellou/kevin/advent/year2018/Day4.java) | ⭐ | ⭐ |
| [Day 5](https://adventofcode.com/2018/day/5) | [Day5.java](src/main/java/com/belellou/kevin/advent/year2018/Day5.java) | ⭐ | ⭐ |
| [Day 6](https://adventofcode.com/2018/day/6) | [Day6.java](src/main/java/com/belellou/kevin/advent/year2018/Day6.java) | ⭐ | ⭐ |
| [Day 7](https://adventofcode.com/2018/day/7) | [Day7.java](src/main/java/com/belellou/kevin/advent/year2018/Day7.java) | ⭐ | ⭐ |
| [Day 8](https://adventofcode.com/2018/day/8) | [Day8.java](src/main/java/com/belellou/kevin/advent/year2018/Day8.java) | ⭐ | ⭐ |
| [Day 9](https://adventofcode.com/2018/day/9) | [Day9.java](src/main/java/com/belellou/kevin/advent/year2018/Day9.java) | ⭐ | ⭐ |
| [Day 10](https://adventofcode.com/2018/day/10) | [Day10.java](src/main/java/com/belellou/kevin/advent/year2018/Day10.java) | ⭐ | ⭐ |
<!--- advent_readme_stars 2018 table --->

[adventofcode]: https://adventofcode.com/

[file_license]: https://github.com/kevin-belellou/advent-of-code/blob/main/LICENSE

[actions_tests]: https://github.com/kevin-belellou/advent-of-code/actions/workflows/maven.yml?query=branch%3Amain
[actions_build]: https://github.com/kevin-belellou/advent-of-code/actions/workflows/maven.yml
[actions_qodana]: https://github.com/kevin-belellou/advent-of-code/actions/workflows/qodana_code_quality.yml
[actions_update_readme]: https://github.com/kevin-belellou/advent-of-code/actions/workflows/update-readme-stars.yml
[actions_dependabot_updates]: https://github.com/kevin-belellou/advent-of-code/actions/workflows/dependabot/dependabot-updates

[badge_license]: https://img.shields.io/github/license/kevin-belellou/advent-of-code?style=plastic
[badge_java_version]: https://img.shields.io/badge/Java-25-blue?style=plastic
[badge_test_results]: https://img.shields.io/endpoint?url=https%3A%2F%2Fgist.githubusercontent.com%2Fkevin-belellou%2Fc098f5fa219b3aa17fd8dabf9087cc14%2Fraw%2Fadvent-of-code-junit-tests.json&style=plastic
[badge_code_coverage]: https://img.shields.io/endpoint?url=https%3A%2F%2Fgist.githubusercontent.com%2Fkevin-belellou%2Fc098f5fa219b3aa17fd8dabf9087cc14%2Fraw%2Fadvent-of-code-jacoco-coverage.json&style=plastic
[badge_actions_build]: https://github.com/kevin-belellou/advent-of-code/actions/workflows/maven.yml/badge.svg?branch=main
[badge_actions_qodana]: https://github.com/kevin-belellou/advent-of-code/actions/workflows/qodana_code_quality.yml/badge.svg?branch=main
[badge_actions_update_readme]: https://github.com/kevin-belellou/advent-of-code/actions/workflows/update-readme-stars.yml/badge.svg?branch=main
[badge_actions_dependabot_updates]: https://github.com/kevin-belellou/advent-of-code/actions/workflows/dependabot/dependabot-updates/badge.svg?branch=main
[badge_stars_2015]: https://img.shields.io/badge/%E2%AD%90_in_2015-41_%2F_50-yellow
[badge_stars_2016]: https://img.shields.io/badge/%E2%AD%90_in_2016-36_%2F_50-yellow
[badge_stars_2017]: https://img.shields.io/badge/%E2%AD%90_in_2017-40_%2F_50-yellow
[badge_stars_2018]: https://img.shields.io/badge/%E2%AD%90_in_2018-20_%2F_50-yellow

[//]: # (@formatter:on)
