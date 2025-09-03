# SKYWATCH

**System Kontrol Yearning Wideband Analysis Terminal Control Hub**

A Hollywood-style aircraft cockpit dashboard for real-time system monitoring, bringing the cinematic experience of a fighter jet HUD to your server metrics.

![Java](https://img.shields.io/badge/Java-21-orange)
![JavaFX](https://img.shields.io/badge/JavaFX-21-blue)
![Status](https://img.shields.io/badge/Status-Operational-green)

## Overview

SKYWATCH transforms mundane system monitoring into an immersive cockpit experience. Inspired by aviation instruments and Hollywood's portrayal of high-tech command centers, this JavaFX application presents server metrics through authentic aircraft gauges and displays.

## Features

### Primary Flight Instruments
- **Attitude Indicator** - CPU Load visualization with pitch ladder and horizon line
- **Altimeter** - Memory usage with analog needle and digital readout
- **Airspeed Indicator** - Network I/O with color-coded speed ranges
- **Vertical Speed Indicator** - Disk I/O rate display
- **Engine Temperature Gauge** - Server temperature monitoring with warning zones
- **Heading Indicator** - Response time with rotating compass rose

### Cockpit Systems
- **Master Caution Panel** - Warning lights and system status indicators
- **ECAM Display** - Real-time alert system with prioritized warnings
- **System Log Terminal** - Streaming server events in authentic green phosphor
- **Performance Throttles** - Manual control sliders for metrics simulation
- **Autopilot Panel** - Toggleable system automation controls
- **Cluster Communication** - Node frequency displays

### Visual Design
- Authentic cockpit color scheme (green, amber, red indicators)
- Full-screen immersive experience
- Animated gauges with smooth transitions
- Blinking alerts for critical conditions
- Military-grade monospace fonts
- Glassmorphic panel effects

## Requirements

- Java 21 or higher
- JavaFX 21
- Maven 3.6+
- macOS, Windows, or Linux

## Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd skywatch
```

2. Build the project:
```bash
mvn clean compile
```

## Running SKYWATCH

### Using Maven
```bash
JAVA_HOME=/path/to/java21 mvn javafx:run
```

### Alternative for Java 21+
```bash
mvn clean javafx:run
```

## Controls

- **ESC** - Exit fullscreen and close application
- **Performance Throttles** - Drag sliders to adjust simulated metrics
- **Autopilot Buttons** - Click to toggle system states
- **Mouse** - Hover over gauges for interactive effects

## Architecture

SKYWATCH is built with JavaFX, leveraging:
- Custom gauge components using Canvas and Shape APIs
- Property bindings for reactive UI updates
- Timeline animations for smooth gauge movements
- CSS styling for authentic cockpit aesthetics

## Metrics Displayed

| Gauge | Metric | Range | Warning Threshold |
|-------|--------|-------|-------------------|
| CPU Load | Processor utilization | 0-100% | >80% (Red) |
| Memory | RAM usage | 0-100% | >85% (Amber) |
| Network I/O | Bandwidth IN/OUT | 0-500 Mb/s | >400 Mb/s (Red) |
| Disk I/O | Transfer rate | 0-100 MB/s | >90 MB/s (Amber) |
| Temperature | System temp | 40-95°C | >75°C (Amber), >85°C (Red) |
| Response Time | Latency | 1-200ms | >150ms (Amber) |

## Customization

The dashboard currently uses simulated data. To connect real system metrics:

1. Replace the simulation in `startSimulation()` method
2. Integrate with system monitoring APIs (JMX, SIGAR, or custom endpoints)
3. Update the property bindings to real data sources

## Future Enhancements

- [ ] Real system metrics integration
- [ ] Multi-node cluster monitoring
- [ ] Data persistence and historical graphs
- [ ] Alert notification system
- [ ] Custom gauge configurations
- [ ] Network topology visualization
- [ ] Sound effects for critical alerts
- [ ] Recording and playback of metrics

## Screenshots

The dashboard features a full-screen cockpit view with:
- Six primary flight instruments arranged in standard T-configuration
- Left panel with server parameters and resource utilization
- Right panel with ECAM alerts and system log
- Bottom panel with throttle controls and autopilot systems

## License

This project is available for educational and monitoring purposes.

## Acknowledgments

Inspired by:
- Boeing 777 and Airbus A350 cockpit designs
- Hollywood representations in Top Gun, Iron Man, and The Matrix
- Real aviation instruments and EFIS displays
- Military command center aesthetics

---

**SKYWATCH** - Where system monitoring meets cinematic excellence. 

*"Tower, this is SKYWATCH. All systems nominal, ready for deployment."*