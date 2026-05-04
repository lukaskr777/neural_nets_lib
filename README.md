# Neural Network Library from Scratch

Java neural-network experiments implemented without external ML libraries.

This repository contains two related folders:

- `RNN/`: a small layer-based neural-network implementation.
- `neural_networks/`: earlier experiments around feed-forward networks, XOR training, and digit recognition.

## RNN

`RNN/` implements a basic neural-network structure using custom Java classes.

Features:

- Custom `Matrix` class for vector and matrix operations
- `Layer` abstraction with `InputLayer`, `Dense`, and `OutputLayer`
- Forward propagation through connected layers
- Backpropagation with weight and bias updates
- Activation functions: ReLU, step, signum, tanh, identity
- Example training loop in `Run.java`

## neural_networks

`neural_networks/` contains earlier from-scratch neural-network experiments.

Features:

- Feed-forward network code using plain Java arrays
- Numerical-gradient training in `Convolutional.java`
- XOR training demos in `Run.java` and `xor_neural/`
- MNIST IDX file parsing in `SampleDatabase.java`
- Conversion of MNIST samples into PNG files
- Simple digit-recognition experiment using saved neuron weights

Data folders:

- `train_samples/`: MNIST IDX image and label files
- `train_samples_db/`: generated training sample images
- `initial_values/`: initial digit templates
- `neuron_database/`: saved neuron relation values

## Running

Compile and run the `RNN/` example:

```powershell
cd RNN
javac *.java
java Run
```

Compile and run the `neural_networks/` example:

```powershell
cd neural_networks
javac *.java
java Run
```

## Notes

- The code is written in plain Java.
- No external machine-learning libraries are used.
- The focus is learning and implementing neural-network mechanics from scratch.
