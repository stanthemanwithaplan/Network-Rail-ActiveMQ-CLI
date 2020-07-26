# Network Rail TD in Java
A Java written Network Rail data feed train describer interpreter

Capability:
- Select a signal area
- Display signal changes
- Display train movments
- Interperate SOP data

# Usage
With a SOP table the program will interpret the data and display the signals
```
Main.jar BP TD_ALL_SIG_AREA example@email.com password sopTable.json
```
Where as without a SOP table the program will simply display the address and raw binary
```
Main.jar BP TD_ALL_SIG_AREA example@email.com password
```

SOP table example
```
{
    "$type": "NetworkRail.ActiveMQ.Support.SOP",
    "BP": {
        "SOP": {
            "0": {
                "0": "SPW5203",
                "1": "SPW5201",
                "2": "SPW5201",
                "3": "SPW5191",
                "4": "SPW5188",
                "5": "SPW5186",
                "6": "SPW5185",
                "7": "SPW5183"
            },
            "1": {
                "0": "SPW5740",
                "1": "SPW5693",
                "2": "SPW5690",
                "3": "SPW5216",
                "4": "SPW5215",
                "5": "SPW5213",
                "6": "SPW5212",
                "7": "SPW5205"
            },
            "2": {
                "0": "SPW5602",
                "1": "SPW5217",
                "2": "SPW5210",
                "3": "SPW5200",
                "4": "SPW5198"
                ...
```
