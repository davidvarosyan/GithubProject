Notes 

Error handling
  The current implementation provides a basic safeCall skeleton with generic error messaging.
  Error handling is very light here; for production, differentiate:

  1.Network errors (connectivity issues, timeouts)

  2.Database errors (cache failures)

  3.Other failures (parsing errors, unexpected crashes)
    Surface specific UI messages or retry strategies per error type.

Layer segregation

  For simplicity, Use Case interfaces and their implementations live in the same module.
  In a larger project, separate out your Use Case interfaces from implementations, and extract the entire Domain layer (models, repositories, use case interfaces) into its own Gradle module to enforce strict boundaries and reduce coupling.

UI polish
  Screens are built as a visual proof‑of‑concept only.
