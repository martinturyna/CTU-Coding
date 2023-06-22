import matplotlib.pyplot as plt
import seaborn as sns
from matplotlib.patches import Rectangle
import matplotlib
import numpy as np
import warnings
from abc import ABC
from ZUI_MDP import *

if __name__ == '__main__':

    name = '3x4'
    x = np.linspace(0, 1, 100)  # action probabilities
    y = np.empty([7, x.size])

    counter = 0
    states = np.array([0, 3, 6, 8, 9, 10, 11])

    policy1 = None  # policy - 1
    policy2 = None  # policy

    thresholds = []
    threshold_value = None

    for action_p in x:

        gw = GridWorld.get_world(name, action_proba = action_p)
        Q = gw.value_iteration()
        V = gw.Q2V(Q)
        policy2 = gw.Q2policy(Q)

        # Checking whether the policy is switched, if yes, add the action_proba from last iter in threshold
        if policy1 is not None and not np.array_equal(policy1, policy2):
            thresholds.append(threshold_value)

        for state in range(0, 7):
            y[state][counter] = V[states[state]]

        policy1 = policy2
        threshold_value = action_p
        counter += 1

    for th in thresholds:
        plt.axvline(x=th, color='r', linestyle='--')

    plt.plot(x, y[0], label='0')
    plt.plot(x, y[1], label='3')
    plt.plot(x, y[2], label='6')
    plt.plot(x, y[3], label='8')
    plt.plot(x, y[4], label='9')
    plt.plot(x, y[5], label='10')
    plt.plot(x, y[6], label='11')
    plt.legend(loc='lower right')
    plt.axis([0.0, 1.0, 0.0, 1.01])
    plt.ylabel('value')
    plt.xlabel('p')
    plt.show()
    # Output - action_proba values, where the policy is switched
    print(thresholds)