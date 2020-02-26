import json
import sys
import xmltodict

if __name__ == "__main__":
    if len(sys.argv) > 1:
        filename = sys.argv[1]
        file_contents = open(filename, 'r+').readlines()
        value1 = None
        value2 = None
        different_exception_type_key = 'different_exception_types'
        different_exception_message_key = 'different_exception_messages'
        different_values_key = 'different_values'
        different_exception_value_key = 'different_exception_value'
        results = {different_exception_type_key: 0, different_exception_message_key: 0, different_values_key: 0, different_exception_value_key: 0}
        exceptions_type = set()
        exceptions_message = set()
        exceptions_values = set()
        row_tokens2 = None
        exception_tag = '<exception>'
        exception_dict1 = None
        exception_dict2 = None
        java_types = 0
        for row in file_contents:
            if '***Different' in row or '***Specific' in row:
                row_tokens1 = row.split(':')
                value1 = row_tokens1[2]
            if '.txt-' in row and not '->' in row:
                row_tokens2 = row.split('txt-')
            if '->' in row:
                row_tokens2 = row.split(':')
            if row_tokens2:
                value2 = row_tokens2[1]
                row_tokens2 = None

            if value1 and value2:
                if exception_tag in value1:
                    exception_dict1 = xmltodict.parse(value1)
                if exception_tag in value2:
                    exception_dict2 = xmltodict.parse(value2)
                if exception_dict1 and exception_dict2:
                    if exception_dict1['exception']['type'] != exception_dict2['exception']['type']:
                        count = results.get(different_exception_type_key) + 1
                        results[different_exception_type_key] = count
                        exceptions_type.add((exception_dict1['exception']['type'], exception_dict2['exception']['type']))
                    if exception_dict1['exception']['message'] != exception_dict2['exception']['message']:
                        count = results.get(different_exception_message_key) + 1
                        results[different_exception_message_key] = count
                        exceptions_message.add(
                            (exception_dict1['exception']['type'], exception_dict1['exception']['message'], exception_dict2['exception']['message']))
                elif exception_dict1 and not exception_dict2 or exception_dict2 and not exception_dict1:
                    count = results.get(different_exception_value_key) + 1
                    results[different_exception_value_key] = count
                    if exception_tag in value1:
                        exceptions_values.add((value1, value2))
                    if exception_tag in value2:
                        exceptions_values.add((value2, value1))
                elif not exception_dict1 and not exception_dict2:
		    # ignoring input programs implemented by JVM
                    if not ('java.' in value1 or 'java.' in value2 or 'javax.' in value1 or 'javax.' in value2):
		            count = results.get(different_values_key) + 1
		            results[different_values_key] = count
                else:
                    print value1, value2
                exception_dict1 = None
                exception_dict2 = None
                value1 = None
                value2 = None

        total = 0
        for k, v in results.iteritems():
            total += v/2
        for k, v in results.iteritems():
            count = v/2
            results[k] = count
            percentage = 1.0*count/total*100
            result = '{k}: {c} {p}%'.format(k=k, c=count, p=percentage)
            print result


    else:
        print 'You must provide a file name'
