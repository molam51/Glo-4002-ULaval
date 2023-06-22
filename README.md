# Team Conventions

## Branch Naming
- `story-short-name/feature-name`

## Commit Naming
- Short & sweet

## Test Naming
- `givenContext_whenAction_thenBehavior`

## Pull Requests
- Short & sweet
- Squash & merge
- Add related Asana tasks' link in PR
- Only the author can resolve a conversation (except urgency)
- 2 approvals from reviewers and 1 from formalism officer (William Pedneault)

# About Our Architecture
- We have three aggregate roots – `CoffeeShop`, `Customer` and `Seat` – instead of one to prevent `CoffeeShop`, `Cube` and `Seat` from becoming god classes.

# Useful Links

- [Asana Board](https://app.asana.com/0/1202991260677568/board)
