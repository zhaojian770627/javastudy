from collections import deque


def movingMax(arr, k):
    n = len(arr)
    Qi = deque()
    
    # Process first k (or first window) 
    # elements of array
    for i in range(k):
        # For every element, the previous 
        # smaller elements are useless
        # so remove them from Qi
        while Qi and arr[i] >= arr[Qi[-1]] :
            Qi.pop()
        
        # Add new element at rear of queue
        Qi.append(i);
        
    # Process rest of the elements, i.e. 
    # from arr[k] to arr[n-1]
    for i in range(k, n):
        
        # The element at the front of the
        # queue is the largest element of
        # previous window, so print it
        print(str(arr[Qi[0]]) + " ", end="")
        
        # Remove the elements which are 
        # out of this window
        while Qi and Qi[0] <= i - k:
            
            # remove from front of deque
            Qi.popleft() 
        
        # Remove all elements smaller than
        # the currently being added element 
        # (Remove useless elements)
        while Qi and arr[i] >= arr[Qi[-1]] :
            Qi.pop()
        
        # Add current element at the rear of Qi
        Qi.append(i)
    
    # Print the maximum element of last window
    print(str(arr[Qi[0]]))
    
arr = [12, 1, 78, 90, 57, 89, 56]
k = 3
movingMax(arr, k)
