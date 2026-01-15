# Dungeonmania – Refactoring & Feature Evolution (Java)

A Java-based dungeon game project focused on **software design, maintainability, and iterative delivery**.  
The core work centres around improving an existing codebase through refactoring and design patterns, then extending the system with new gameplay requirements while keeping the system stable and well-tested.

> **Note:** This repository is a portfolio-friendly adaptation of a university computer science project.  
> Course-specific specification text, marking rubrics, and blogging templates have been removed. This public version focuses on the implementation and engineering outcomes.

---

## Project Overview

This project simulates a real-world scenario: inheriting a large codebase, identifying design flaws, improving structure and extensibility, and then delivering new product features under evolving requirements.

Key themes include:
- Code quality improvements (smells, cohesion/coupling, abstraction)
- Design patterns and principles (e.g., DRY, Open/Closed, Law of Demeter, Liskov)
- Backwards compatibility (existing behaviours must remain correct)
- Testing discipline (new tests + regression stability)
- Incremental delivery (small changes, review-friendly commits)

---

## What I Worked On

### 1) Code Analysis & Refactoring
Improved the design of an existing monolith by:
- Removing repetition via appropriate design patterns
- Identifying and improving an Observer-style relationship in the codebase
- Fixing problematic inheritance and "empty override" smells
- Refactoring collectable interactions to improve cohesion and reduce ripple effects
- Improving goal logic design to better support extension
- Additional refactors to reduce hard-coding and improve maintainability

### 2) Evolution of Requirements (Feature Delivery)
Extended the game with new requirements (examples may include):
- New goal types and win conditions
- New enemy behaviour and combat rules
- New map tiles that affect movement and pathfinding
- New collectables/buildables and crafting constraints
- More complex moving entities and logic-based interactions

All changes were implemented with an emphasis on **clean integration**, **minimal breakage**, and **test coverage**.

### 3) Investigation & Verification
Reviewed behaviour against requirements, identified edge cases, and fixed any mismatches—treating it like a production “bug bash” and validation pass.

---

## Tech Stack

- **Java**
- **Gradle**
- **JUnit** (tests)
- CI-style workflow (keep tests + style checks passing)

---

#
