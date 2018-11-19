#!/bin/bash

# DISCRETE COLUMN
/home/gpadmin/test/c/initial_tag_by_column.sh t_customer id DISCRETE gender,cus_level,edu 10000000

# CONTINUOUS COLUMN
/home/gpadmin/test/c/initial_tag_by_column.sh t_customer id CONTINUOUS age 10 90 10 10000000
/home/gpadmin/test/c/initial_tag_by_column.sh t_customer id CONTINUOUS income 10000 30000 5000 10000000
/home/gpadmin/test/c/initial_tag_by_column.sh t_customer id CONTINUOUS stars 1000 5000 1000 10000000